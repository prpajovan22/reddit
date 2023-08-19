package com.ftn.reddit.controller;

import co.elastic.clients.elasticsearch.nodes.Http;
import com.ftn.reddit.DTO.CommunityDTO;
import com.ftn.reddit.DTO.PostDTO;
import com.ftn.reddit.model.Community;
import com.ftn.reddit.model.Post;
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

    @PostMapping
    public ResponseEntity<Void> createCommunity(@RequestBody @Validated CommunityDTO communityDTO, Authentication authentication) {
        //UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        //Users users = userService.findByUsername(userPrincipal.getUsername());
        LocalDate creationDate = LocalDate.now();
        Community community = new Community();
        community.setName(communityDTO.getName());
        community.setDescription(communityDTO.getDescription());
        community.setCreationDate(creationDate);
        community.setSuspended(false);
        //community.setModerators((Set<Users>) users);
        communityService.save(community);
        return new ResponseEntity<Void>(HttpStatus.OK);
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

    @GetMapping("/community/{id}/postCount")
    public ResponseEntity<Long> getPostCount(@PathVariable Integer id) {
        Long postCount = communityService.getPostCountForCommunity(id);
        return ResponseEntity.ok(postCount);
    }
}
