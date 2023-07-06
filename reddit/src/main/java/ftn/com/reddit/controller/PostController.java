package ftn.com.reddit.controller;

import ftn.com.reddit.DTO.PostDTO;
import ftn.com.reddit.models.Community;
import ftn.com.reddit.models.Post;
import ftn.com.reddit.models.Users;
import ftn.com.reddit.repositorys.PostRepository;
import ftn.com.reddit.services.CommunityService;
import ftn.com.reddit.services.PostService;
import ftn.com.reddit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping(value = "/createPost/{id}")
    public ResponseEntity<Void> createPost(@RequestBody @Validated @PathVariable("id") Integer id, PostDTO postDTO, Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Users users = userService.findByUsername(userPrincipal.getUsername());
        Community community = communityService.findById(id);
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setText(postDTO.getText());
        post.setCreationDate(postDTO.getCreationDate());
        post.setPostPDFPath(postDTO.getPostPDFPath());
        post.setCommunity(community);
        post.setUser(users);
        postService.save(post);
        return new ResponseEntity<Void>(HttpStatus.OK);


    }

    @DeleteMapping(value = "/akcije/{id}")
    @PreAuthorize("hasAnyRole('PRODAVAC')")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Integer id, Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Post post = postService.findById(id);
        Users user = userService.findByUsername(userPrincipal.getUsername());
        List<Post> posts = postService.getByUser(user.getId());
        if (!posts.contains(post)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            if (post != null) {
                postService.delete(post);
                return new ResponseEntity<Void>(HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

            }
        }

    }
}
