package com.ftn.reddit.controller;

import com.ftn.reddit.DTO.PostDTO;
import com.ftn.reddit.model.*;
import com.ftn.reddit.services.CommunityService;
import com.ftn.reddit.services.PostService;
import com.ftn.reddit.services.ReactionService;
import com.ftn.reddit.services.UserService;
import jakarta.transaction.Transactional;
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

@RestController
@RequestMapping(value = "post")
@CrossOrigin("*")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private ReactionService reactionService;

    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        List<Post> posts = postService.findAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") Integer id) {
        Post post = postService.findById(id);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(post, HttpStatus.OK);
    }
    @GetMapping(value = "user/{id}")
    public ResponseEntity<Post> getPostByUserId(@PathVariable("id") Integer id){
        Post post = postService.getPostByUsers_User_id(id);
        if (post == null){
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


}
