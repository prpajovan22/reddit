package com.ftn.reddit.controller;

import com.ftn.reddit.DTO.CommentDTO;
import com.ftn.reddit.DTO.CommunityDTO;
import com.ftn.reddit.model.*;
import com.ftn.reddit.model.pretraga.CommentSerachCriteria;
import com.ftn.reddit.services.CommentService;
import com.ftn.reddit.services.PostService;
import com.ftn.reddit.services.ReactionService;
import com.ftn.reddit.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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


    /*@GetMapping
    public ResponseEntity<List<CommentDTO>> getComments() {
        List<Comment> comments = commentService.findAll();

        List<CommentDTO> commentDTOs = comments.stream()
                .map(comment -> {
                    CommentDTO commentDTO = new CommentDTO(comment);
                    return commentDTO;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(commentDTOs, HttpStatus.OK);
    }
*/

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getComments() {
        List<Comment> comments = commentService.findAll();

        List<CommentDTO> commentDTOs = comments.stream()
                .map(comment -> {
                    CommentDTO commentDTO = new CommentDTO(comment);
                    int totalReactions = calculateTotalReactions(comment);
                    commentDTO.setNetReactions(totalReactions);
                    return commentDTO;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(commentDTOs, HttpStatus.OK);
    }

    private int calculateTotalReactions(Comment comment) {
        int totalUpvotes = 0;
        int totalDownvotes = 0;

        for (Reaction reaction : comment.getReactions()) {
            if (ReactionType.UPWOTE.equals(reaction.getType())) {
                totalUpvotes++;
            } else if (ReactionType.DOWNWOTE.equals(reaction.getType())) {
                totalDownvotes++;
            }
        }
        int totalReactions = totalUpvotes - totalDownvotes;

        return totalReactions;
    }

    @PostMapping("/create")
    public ResponseEntity<CommentDTO> createComment(
            @RequestParam Integer post_id,
            @RequestParam String text,
            HttpSession session
    ) {
        Users loggedInUser = (Users) session.getAttribute("loggedUser");

        if (loggedInUser == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<Post> existingPost = Optional.ofNullable(postService.findById(post_id));

        if (!existingPost.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Comment newComment = new Comment();
        newComment.setPost(existingPost.get());
        newComment.setText(text);
        newComment.setTimestamp(LocalDate.now());
        newComment.setDeleted(false);
        newComment.setUser(loggedInUser);

        Comment createdComment = commentService.createComment(newComment);

        if (createdComment != null) {
            CommentDTO createdCommentDTO = new CommentDTO(createdComment);
            return new ResponseEntity<>(createdCommentDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<Void> createComment(@PathVariable("id") Integer post_id, @RequestBody @Validated CommentDTO commentDTO, HttpSession session) {
        Users loggedInUser = (Users) session.getAttribute("loggedUser");
        Post post = postService.findById(post_id);
        LocalDate creationDate = LocalDate.now();
        Comment comment = new Comment();
        comment.setText(commentDTO.getText());
        comment.setTimestamp(creationDate);
        comment.setDeleted(false);
        comment.setUser(loggedInUser);
        comment.setPost(post);
        commentService.save(comment);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @PutMapping("/{comment_id}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Integer comment_id,
            @RequestPart("text") String text){

        Comment existingComment = commentService.findById(comment_id);
        if (existingComment == null) {
            return ResponseEntity.notFound().build();
        }

        Post originalPost = existingComment.getPost();
        existingComment.setPost(originalPost);
        existingComment.setText(text);
        Comment updated = commentService.save(existingComment);
        return ResponseEntity.ok(updated);
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
            searchResult = commentService.findAll();
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
