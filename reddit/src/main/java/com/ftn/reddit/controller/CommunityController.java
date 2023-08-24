package com.ftn.reddit.controller;

import co.elastic.clients.elasticsearch.nodes.Http;
import com.ftn.reddit.DTO.CommunityDTO;
import com.ftn.reddit.DTO.PostDTO;
import com.ftn.reddit.model.Community;
import com.ftn.reddit.model.Post;
import com.ftn.reddit.model.UserRole;
import com.ftn.reddit.model.Users;
import com.ftn.reddit.model.pretraga.CommunitySearchCriteria;
import com.ftn.reddit.services.CommunityService;
import com.ftn.reddit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "community")
@CrossOrigin("*")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<List<CommunityDTO>> getCommunities() {
        List<Community> communities = communityService.findAll();

        List<CommunityDTO> communityDTOs = communities.stream()
                .filter(community -> !community.isSuspended())
                .map(CommunityDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(communityDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Community> getCommunityById(@PathVariable("id") Integer id) {
        Community community = communityService.findById(id);
        if (community == null && community.isSuspended() == true) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(community, HttpStatus.OK);
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<CommunityDTO> getCommunityByPostId(@PathVariable Integer post_id) {
        CommunityDTO community = communityService.getCommunityByPostId(post_id);
        if (community == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(community);
    }

    @PostMapping
    public ResponseEntity<Void> createCommunity(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "communityPDF", required = false) MultipartFile communityPDF,
            Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Users user = userService.findByUsername(userPrincipal.getUsername());

        if (!user.getUserRole().equals("MODERATOR")) {
            user.setUserRole(UserRole.valueOf("MODERATOR"));
            userService.save(user);
        }

        LocalDate creationDate = LocalDate.now();
        Community community = new Community();
        community.setName(name);
        community.setDescription(description);
        community.setCreationDate(creationDate);
        community.setSuspended(false);
        community.getModerators().add(user); // Add the user as a moderator

        if (communityPDF != null && !communityPDF.isEmpty()) {
            try {
                String communityPDFPath = saveCommunityPDF(communityPDF);
                community.setCommunityPDFPath(communityPDFPath);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        communityService.save(community);

        return ResponseEntity.ok().build();
    }


    @PutMapping("/{community_id}")
    public ResponseEntity<Community> updateCommunity(
            @PathVariable Integer community_id,
            @RequestBody Community updatedCommunity) {

        Community existingCommunity = communityService.findById(community_id);
        if (existingCommunity == null) {
            return ResponseEntity.notFound().build();
        }
        existingCommunity.setName(updatedCommunity.getName());
        existingCommunity.setDescription(updatedCommunity.getDescription());

        Community updated = communityService.save(existingCommunity);
        return ResponseEntity.ok(updated);
    }

    private String saveCommunityPDF(MultipartFile communityPDF) throws IOException {
        String uploadDir = "path/to/your/upload/directory";

        Path uploadPath = Path.of(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String uniqueFileName = System.currentTimeMillis() + "_" + communityPDF.getOriginalFilename();

        Path filePath = uploadPath.resolve(uniqueFileName);

        try {
            Files.copy(communityPDF.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save the PDF file: " + e.getMessage());
        }

        return uploadDir + "/" + uniqueFileName;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable("id") Integer id, Authentication authentication) {
        Community community = communityService.findById(id);
        community.setSuspended(true);
        communityService.save(community);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Community>> seacrchCommunities(@RequestBody CommunitySearchCriteria searchCriteria){
        List<Community> searchResault = communityService.searchCommunities(searchCriteria);
        return ResponseEntity.ok(searchResault);
    }

    @GetMapping("/{community_id}/postCount")
    public ResponseEntity<Long> getPostCountForCommunity(@PathVariable Integer community_id) {
        Long postCount = communityService.getPostCountForCommunity(community_id);
        return ResponseEntity.ok(postCount);
    }

    @GetMapping("/{community_id}/totalReactions")
    public ResponseEntity<Double> getAverageReactionsForCommunity(@PathVariable("community_id") Integer community_id) {
        Double averageReactions = communityService.getAverageReactionsPerPost(community_id);
        return ResponseEntity.ok(averageReactions);
    }
}
