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
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/community")
@CrossOrigin("*")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private UserService userService;


    @GetMapping
    public List<Community> findAll() {
        List<Community> allCommunities = communityService.findAllNonSuspended();
        return allCommunities.stream()
                .filter(community -> !community.isSuspended())
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/byId/{id}")
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
    @Transactional
    public ResponseEntity<Void> createCommunity(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "communityPDF", required = false) MultipartFile communityPDF,
            HttpSession session) {
        //Users author = (Users) session.getAttribute("loggedUser");

        Users author = userService.findById(1);

        if (!author.getUserRole().equals("MODERATOR")) {
            author.setUserRole(UserRole.valueOf("MODERATOR"));
            userService.save(author);

        }
        LocalDate creationDate = LocalDate.now();
        Community community = new Community();
        community.setName(name);
        community.setDescription(description);
        community.setCreationDate(creationDate);
        community.setSuspended(false);
        community.getModerators().add(author);

        if (communityPDF != null && !communityPDF.isEmpty()) {
            String pdfFilePath = savePDFFile(communityPDF);
            community.setCommunityPDFPath(pdfFilePath);

            String originalFilename = communityPDF.getOriginalFilename();
            if (originalFilename != null && !originalFilename.isEmpty()) {
                community.setCommunityPDFName(originalFilename);
            }
        }


        communityService.save(community);

        return ResponseEntity.ok().build();
    }


    @PutMapping("/{community_id}")
    public ResponseEntity<Community> updateCommunity(
            @PathVariable Integer community_id,
            @RequestParam(value = "communityPDF", required = false) MultipartFile communityPDF,
            @RequestPart("name") String name,
            @RequestPart("description") String description) {

        Community existingCommunity = communityService.findById(community_id);
        if (existingCommunity == null) {
            return ResponseEntity.notFound().build();
        }

        existingCommunity.setName(name);
        existingCommunity.setDescription(description);

        if (communityPDF != null && !communityPDF.isEmpty()) {
            String pdfFilePath = savePDFFile(communityPDF);
            existingCommunity.setCommunityPDFPath(pdfFilePath);

            String originalFilename = communityPDF.getOriginalFilename();
            if (originalFilename != null && !originalFilename.isEmpty()) {
                existingCommunity.setCommunityPDFName(originalFilename);
            }
        }

        Community updated = communityService.save(existingCommunity);
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

    private String saveCommunityPDF(MultipartFile communityPDF) throws IOException {
        String uploadDir = "files";
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

    @DeleteMapping(value = "/{community_id}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable("community_id") Integer community_id, Authentication authentication) {
        Community community = communityService.findById(community_id);
        community.setSuspended(true);
        communityService.save(community);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Community>> seacrchCommunities(@RequestBody CommunitySearchCriteria searchCriteria) {
        List<Community> searchResult = communityService.searchCommunities(searchCriteria);

        List<Community> filteredResult = searchResult.stream()
                .filter(community -> !community.isSuspended())
                .collect(Collectors.toList());

        return ResponseEntity.ok(filteredResult);
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
