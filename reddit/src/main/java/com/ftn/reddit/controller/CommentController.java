package com.ftn.reddit.controller;

import com.ftn.reddit.DTO.CommentDTO;
import com.ftn.reddit.DTO.CommunityDTO;
import com.ftn.reddit.model.Comment;
import com.ftn.reddit.model.Community;
import com.ftn.reddit.model.Post;
import com.ftn.reddit.model.Users;
import com.ftn.reddit.services.CommentService;
import com.ftn.reddit.services.PostService;
import com.ftn.reddit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/comment")
@CrossOrigin("*")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getComments() {
        List<Comment> comments = commentService.findAll();

        List<CommentDTO> commentDTOs = comments.stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(commentDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable("id") Integer id) {
        Comment comment = commentService.findById(id);
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }


    @PostMapping(value = "/createComment")
    public ResponseEntity<Void> createComment(@PathVariable("id") Integer post_id,@RequestBody @Validated CommentDTO commentDTO, Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Users users = userService.findByUsername(userPrincipal.getUsername());
        Post post = postService.findById(post_id);
        LocalDate creationDate = LocalDate.now();
        Comment comment = new Comment();
        comment.setText(commentDTO.getText());
        comment.setTimestamp(creationDate);
        comment.setDeleted(false);
        comment.setUsers(users);
        comment.setPost(post);
        commentService.save(comment);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Integer id, Authentication authentication) {
        Comment comment = commentService.findById(id);
        if(comment.isDeleted()){
            comment.setDeleted(true);
            commentService.save(comment);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
