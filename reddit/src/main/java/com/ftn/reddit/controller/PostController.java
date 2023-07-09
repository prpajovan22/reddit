package com.ftn.reddit.controller;

import com.ftn.reddit.DTO.PostDTO;
import com.ftn.reddit.services.CommunityService;
import com.ftn.reddit.services.PostService;
import com.ftn.reddit.services.UserService;
import com.ftn.reddit.model.Community;
import com.ftn.reddit.model.Post;
import com.ftn.reddit.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/createPost/")
    public ResponseEntity<PostDTO> createPost(@RequestBody @Validated @PathVariable("id") Integer id, PostDTO postDTO, Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Users users = userService.findByUsername(userPrincipal.getUsername());
        Community community = communityService.findById(id);
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setText(postDTO.getText());
        post.setCreationDate(postDTO.getCreationDate());
        post.setPostPDFPath(postDTO.getPostPDFPath());
        post.setCommunity(community);
        post.setUsers(users);
        post = postService.save(post);
        return new ResponseEntity<PostDTO>(new PostDTO(post), HttpStatus.CREATED);


    }

    @DeleteMapping(value = "/post/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Integer id, Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Post post = postService.findById(id);
        Users user = userService.findByUsername(userPrincipal.getUsername());
        return null;

    }
}
