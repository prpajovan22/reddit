package com.ftn.reddit.controller;

import com.ftn.reddit.DTO.CommentDTO;
import com.ftn.reddit.DTO.CommunityDTO;
import com.ftn.reddit.model.Comment;
import com.ftn.reddit.model.Community;
import com.ftn.reddit.model.Post;
import com.ftn.reddit.model.Users;
import com.ftn.reddit.model.pretraga.CommentSerachCriteria;
import com.ftn.reddit.services.CommentService;
import com.ftn.reddit.services.PostService;
import com.ftn.reddit.services.ReactionService;
import com.ftn.reddit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
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

    @Autowired
    private ReactionService reactionService;


    @GetMapping
    public ResponseEntity<List<CommentDTO>> getComments() {
        List<Comment> comments = commentService.findAll();

        List<CommentDTO> commentDTOs = comments.stream()
                .map(comment -> {
                    CommentDTO commentDTO = new CommentDTO(comment);
                    /*int netReactions = commentService.calculateNetReactionsForComments(comment.getReactions());
                    commentDTO.setNetReactions(netReactions);
                    */return commentDTO;
                })
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
        comment.setUser(users);
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

    @GetMapping("/byPost/{post_id}")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable Integer post_id) {
        Post post = postService.findById(post_id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        List<Comment> comments = commentService.getCommentsByPost(post);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/searchInPost/{post_id}")
    public ResponseEntity<List<Comment>> searchCommentsInPost(
            @PathVariable Integer post_id, @RequestBody CommentSerachCriteria searchCriteria
    ) {
        Post post = postService.findById(post_id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        List<Comment> searchResults = commentService.searchCommentsInPost(post, searchCriteria);
        return ResponseEntity.ok(searchResults);
    }

    @GetMapping("/{comment_id}/replies")
    public ResponseEntity<List<Comment>> getRepliesForComment(@PathVariable Integer comment_id) {
        Comment comment = commentService.findById(comment_id);
        if (comment == null) {
            return ResponseEntity.notFound().build();
        }

        List<Comment> replies = commentService.getRepliesForComment(comment);
        return ResponseEntity.ok(replies);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Comment>> searchComments(@RequestBody CommentSerachCriteria searchCriteria) {
        List<Comment> searchResult;

        if (StringUtils.isEmpty(searchCriteria.getText())) {
            searchResult = commentService.findAll(); // Fetch all comments when text is empty
        } else {
            searchResult = commentService.searchCommentsByTextContainingIgnoreCase(searchCriteria.getText());
        }

        return ResponseEntity.ok(searchResult);
    }

    @PostMapping("/upvote/{comment_id}")
    public ResponseEntity<Void> upvoteComment(@PathVariable Integer comment_id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Users user = userService.findByUsername(userDetails.getUsername());

        reactionService.upvoteComment(comment_id, user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/downvote/{comment_id}")
    public ResponseEntity<Void> downvoteComment(@PathVariable Integer comment_id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Users user = userService.findByUsername(userDetails.getUsername());

        reactionService.downvoteComment(comment_id, user);
        return ResponseEntity.ok().build();
    }
}
