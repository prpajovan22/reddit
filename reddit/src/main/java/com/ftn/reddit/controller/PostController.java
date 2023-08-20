package com.ftn.reddit.controller;

import co.elastic.clients.elasticsearch.nodes.Ingest;
import com.ftn.reddit.DTO.CommentDTO;
import com.ftn.reddit.DTO.PostDTO;
import com.ftn.reddit.DTO.ReactionDTO;
import com.ftn.reddit.model.*;
import com.ftn.reddit.model.pretraga.CommunitySearchCriteria;
import com.ftn.reddit.model.pretraga.PostSearchCriteria;
import com.ftn.reddit.services.*;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/post")
@CrossOrigin("*")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private ReactionService reactionService;

    /*@GetMapping
    public ResponseEntity<List<PostDTO>> getPosts() {
        List<Post> posts = postService.findAll();

        List<PostDTO> postDTOs = posts.stream()
                .filter(post -> !communityService.isSuspended(post.getCommunity().getCommunity_id()))
                .map(PostDTO::new)
                .collect(Collectors.toList());

        Collections.shuffle(postDTOs);
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
    }*/

    @GetMapping
    public ResponseEntity<List<PostDTO>> getPosts() {
        List<Post> posts = postService.findAll();

        List<PostDTO> postDTOs = posts.stream()
                .filter(post -> !communityService.isSuspended(post.getCommunity().getCommunity_id()))
                .map(post -> new PostDTO(post, reactionService.getNetReactionForPost(post)))
                .collect(Collectors.toList());

        Collections.shuffle(postDTOs);
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") Integer id) {
        Post post = postService.findById(id);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping("/create/{communityId}")
    public ResponseEntity<Void> createPost(
            @PathVariable Integer community_id,
            @RequestBody PostDTO postRequest,
            Authentication authentication
    ) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Users author = userService.findByUsername(userDetails.getUsername());

        Community community = communityService.findById(community_id);
        if (community == null) {
            return ResponseEntity.notFound().build();
        }

        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setText(postRequest.getText());
        post.setCommunity(community);
        post.setUsers(author);
        post.setCreationDate(LocalDate.from(LocalDateTime.now()));

        postService.save(post);

        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Integer id, Authentication authentication) {
        Post post = postService.findById(id);
        postService.delete(post);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/search")
    public ResponseEntity<List<Post>> seacrchPosts(@RequestBody PostSearchCriteria searchCriteria) {
        List<Post> searchResults = postService.searchPosts(searchCriteria);
        return ResponseEntity.ok(searchResults);
    }

    ////////////////////////////////// Community  ////////////////////////////////////////

    @GetMapping("/byCommunity/{community_id}")
    public ResponseEntity<List<Post>> getPostsByCommunity(@PathVariable Integer community_id){
        Community community = communityService.findById(community_id);
        if(community == null){
            return ResponseEntity.notFound().build();
        }
        List<Post> posts = postService.getPostsByCommunity(community);
        return ResponseEntity.ok(posts);
    }

    ////////////////////////////////// Comments  ////////////////////////////////////////


    @GetMapping("/{post_id}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable Integer post_id) {
        Post post = postService.findById(post_id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        List<CommentDTO> commentDTOs = commentService.findCommentDTOsByPost(post);
        return ResponseEntity.ok(commentDTOs);
    }

    ////////////////////////////////// Upwote  ////////////////////////////////////////

    @PostMapping("/upvote/{post_id}")
    public ResponseEntity<Void> upvotePost(@PathVariable Integer post_id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Users user = userService.findByUsername(userDetails.getUsername());

        reactionService.upvotePost(post_id, user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/downvote/{post_id}")
    public ResponseEntity<Void> downvotePost(@PathVariable Integer post_id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Users user = userService.findByUsername(userDetails.getUsername());

        reactionService.downvotePost(post_id, user);
        return ResponseEntity.ok().build();
    }
}
