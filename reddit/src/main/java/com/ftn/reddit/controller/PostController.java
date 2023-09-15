package com.ftn.reddit.controller;

import co.elastic.clients.elasticsearch.nodes.Ingest;
import com.ftn.reddit.DTO.CommentDTO;
import com.ftn.reddit.DTO.PostDTO;
import com.ftn.reddit.DTO.ReactionDTO;
import com.ftn.reddit.model.*;
import com.ftn.reddit.model.pretraga.CommunitySearchCriteria;
import com.ftn.reddit.model.pretraga.PostSearchCriteria;
import com.ftn.reddit.services.*;
import jakarta.servlet.http.HttpSession;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    @GetMapping
    public ResponseEntity<List<PostDTO>> getPosts() {
        List<Post> posts = postService.findAll();

        List<PostDTO> postDTOs = posts.stream()
                .filter(post -> !communityService.isSuspended(post.getCommunity().getCommunity_id()))
                .map(post -> {
                    int netReactions = calculateNetReactions(post.getReactions());
                    return new PostDTO(post, netReactions);
                })
                .collect(Collectors.toList());

        Collections.shuffle(postDTOs);
        return ResponseEntity.ok(postDTOs);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") Integer id) {
        Post post = postService.findById(id);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping("/{post_id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable Integer post_id,
            @RequestParam(value = "postPDFPath", required = false) MultipartFile postPDFPath,
            @RequestPart("text") String text,
            @RequestPart("title") String title,
            @RequestPart(value = "descriptionPDF", required = false) String descriptionPDF) {

        Post existingPost = postService.findById(post_id);
        if (existingPost == null) {
            return ResponseEntity.notFound().build();
        }

        Community originalCommunity = existingPost.getCommunity();

        existingPost.setCommunity(originalCommunity);
        existingPost.setTitle(title);
        existingPost.setText(text);

        if (postPDFPath != null && !postPDFPath.isEmpty()) {
            String pdfFilePath = savePDFFile(postPDFPath);
            existingPost.setPostPDFPath(pdfFilePath);
        }

        if (descriptionPDF != null && !descriptionPDF.isEmpty()) {
            existingPost.setDescriptionPDF(descriptionPDF);
        }
        Post updated = postService.save(existingPost);
        return ResponseEntity.ok(updated);
    }

    private String savePDFFile(MultipartFile pdfFile) {
        if (pdfFile == null || pdfFile.isEmpty()) {
            return null;
        }
        String uploadDir = "files";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String uniqueFileName = UUID.randomUUID().toString() + "_" + pdfFile.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, uniqueFileName);
        try {
            Files.copy(pdfFile.getInputStream(), filePath);
            return uniqueFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/create/{community_id}")
    public ResponseEntity<Void> createPost(
            @PathVariable Integer community_id,
            @RequestBody PostDTO postRequest,
            HttpSession session
    ) {
        //user koristiti svuda
        Users author = (Users) session.getAttribute("loggedUser");

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

    @GetMapping("/{post_id}/commentCount")
    public ResponseEntity<Long> getCommentCountForPost(@PathVariable Integer post_id) {
        Long commentCount = postService.getCommentCountForPost(post_id);
        return ResponseEntity.ok(commentCount);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Integer id, Authentication authentication) {
        Post post = postService.findById(id);
        postService.delete(post);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/search")
    public ResponseEntity<List<PostDTO>> searchPosts(@RequestBody PostSearchCriteria searchCriteria) {
        List<Post> searchResults = postService.searchPosts(searchCriteria);

        List<PostDTO> postDTOs = searchResults.stream()
                .filter(post -> !communityService.isSuspended(post.getCommunity().getCommunity_id()))
                .map(post -> {
                    List<ReactionDTO> reactions = reactionService.findByPost(post).stream()
                            .map(ReactionDTO::new)
                            .collect(Collectors.toList());

                    int upvotes = (int) reactions.stream().filter(r -> r.getType() == ReactionType.UPWOTE).count();
                    int downvotes = (int) reactions.stream().filter(r -> r.getType() == ReactionType.DOWNWOTE).count();
                    int netReaction = upvotes - downvotes;

                    return new PostDTO(post, netReaction);
                })
                .collect(Collectors.toList());

        Collections.shuffle(postDTOs);
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
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
    public ResponseEntity<String> upvotePost(@PathVariable Integer post_id, HttpSession session) {
        Users author = (Users) session.getAttribute("loggedUser");

        if (author.isSuspended()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is suspended and cannot vote.");
        }

        reactionService.upvotePost(post_id, author);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/downvote/{post_id}")
    public ResponseEntity<String> downvotePost(@PathVariable Integer post_id, HttpSession session) {
        Users author = (Users) session.getAttribute("loggedUser");

        if (author.isSuspended()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is suspended and cannot vote.");
        }

        reactionService.downvotePost(post_id, author);
        return ResponseEntity.ok().build();
    }

    private int calculateNetReactions(List<Reaction> reactions) {
        int netReactions = 0;
        for (Reaction reaction : reactions) {
            if (reaction.getType() == ReactionType.UPWOTE) {
                netReactions++;
            } else if (reaction.getType() == ReactionType.DOWNWOTE) {
                netReactions--;
            }
        }
        return netReactions;
    }
}
