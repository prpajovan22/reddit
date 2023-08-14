package com.ftn.reddit.controller;

import co.elastic.clients.elasticsearch.nodes.Ingest;
import com.ftn.reddit.DTO.CommentDTO;
import com.ftn.reddit.DTO.PostDTO;
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

    @GetMapping
    public ResponseEntity<List<PostDTO>> getPosts() {
        List<Post> posts = postService.findAll();

        // Filter out posts belonging to suspended communities
        List<PostDTO> postDTOs = posts.stream()
                .filter(post -> !communityService.isSuspended(post.getCommunity().getCommunity_id()))
                .map(PostDTO::new) // Assuming a constructor in PostDTO that takes a Post object
                .collect(Collectors.toList());

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

    @PostMapping(value = "/createPost")
    public ResponseEntity<Void> createPost(@RequestBody @Validated PostDTO postDTO, Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Users users = userService.findByUsername(userPrincipal.getUsername());
        Community community = communityService.findById(postDTO.getCommunity_id());
        Reaction reaction = new Reaction();
        LocalDate registrationDate = LocalDate.now();
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setText(postDTO.getText());
        post.setCreationDate(registrationDate);
        post.setPostPDFPath(post.getPostPDFPath());
        post.setUsers(users);
        post.setCommunity(community);
        postService.save(post);
        reaction.setPost(post);
        reaction.setUser(users);
        reaction.setType(ReactionType.UPWOTE);
        reaction.setTimestamp(registrationDate);
        reactionService.save(reaction);
        return new ResponseEntity<Void>(HttpStatus.OK);
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

    @GetMapping("/byCommunity/{community_id}")
    public ResponseEntity<List<Post>> getPostsByCommunity(@PathVariable Integer community_id){
        Community community = communityService.findById(community_id);
        if(community == null){
            return ResponseEntity.notFound().build();
        }
        List<Post> posts = postService.getPostsByCommunity(community);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{post_id}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable Integer post_id) {
        Post post = postService.findById(post_id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        List<CommentDTO> commentDTOs = commentService.findCommentDTOsByPost(post);
        return ResponseEntity.ok(commentDTOs);
    }

}
