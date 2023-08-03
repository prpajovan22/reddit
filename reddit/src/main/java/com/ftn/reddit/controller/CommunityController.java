package com.ftn.reddit.controller;

import com.ftn.reddit.DTO.CommunityDTO;
import com.ftn.reddit.DTO.PostDTO;
import com.ftn.reddit.model.Community;
import com.ftn.reddit.model.Post;
import com.ftn.reddit.model.Users;
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
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "community")
@CrossOrigin("*")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<List<Community>> getCommunitys() {
        List<Community> communities = communityService.findAll();
        return new ResponseEntity<>(communities, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Community> getCommunityById(@PathVariable("id") Integer id) {
        Community community = communityService.findById(id);
        if (community == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(community, HttpStatus.OK);
    }

    @PostMapping(value = "/createCommunity")
    public ResponseEntity<Void> createCommunity(@RequestBody @Validated CommunityDTO communityDTO, Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Users users = userService.findByUsername(userPrincipal.getUsername());
        LocalDate creationDate = LocalDate.now();
        Community community = new Community();
        community.setName(communityDTO.getName());
        community.setDescription(communityDTO.getDescription());
        community.setCreationDate(creationDate);
        community.setSuspendedReason(communityDTO.getSuspendedReason());
        community.setSuspended(false);
        community.setModerators((Set<Users>) users);
        communityService.save(community);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable("id") Integer id, Authentication authentication) {
        Community community = communityService.findById(id);
        if(community.isSuspended()){
            community.setSuspended(true);
            communityService.save(community);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
