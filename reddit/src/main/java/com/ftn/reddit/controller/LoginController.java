package com.ftn.reddit.controller;

import com.ftn.reddit.DTO.LoginDTO;
import com.ftn.reddit.DTO.UsersDTO;
import com.ftn.reddit.model.Users;
import com.ftn.reddit.security.TokenUtils;
import com.ftn.reddit.security.UserDetailServiceImpl;
import com.ftn.reddit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    TokenUtils tokenUtils;

    @PostMapping("/login")
    public ResponseEntity<UsersDTO> login(@RequestBody @Validated LoginDTO userDto) {

        Users korisnik = userService.findByUsername(userDto.getUsername());
        if (korisnik != null && korisnik.getBanned().isEmpty()) {
            return ResponseEntity.notFound().build();

        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDto.getUsername(), userDto.getPassword());
        try {
            UserDetails userDetails = userDetailService.loadUserByUsername(userDto.getUsername());
            Users users = userService.findByUsername(userDto.getUsername());
            UsersDTO korisnikDTO = new UsersDTO(users);
            korisnikDTO.setToken(tokenUtils.generateToken(userDetails));
            return ResponseEntity.ok(korisnikDTO);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Users> save(@RequestBody UsersDTO usersDTO) {
        if (usersDTO.getUsername() == null || Objects.equals(usersDTO.getUsername(), "") ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (usersDTO.getPassword() == null || Objects.equals(usersDTO.getPassword(), "") ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (usersDTO.getEmail() == null || Objects.equals(usersDTO.getEmail(), "") ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (usersDTO.getAvatar() == null || Objects.equals(usersDTO.getAvatar(), "") ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (usersDTO.getRegistrationDate() == null || Objects.equals(usersDTO.getRegistrationDate(), "") ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (usersDTO.getDescription() == null || Objects.equals(usersDTO.getDescription(), "") ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (usersDTO.getDisplayName() == null || Objects.equals(usersDTO.getDisplayName(), "") ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (usersDTO.getUserRole() == null || Objects.equals(usersDTO.getUserRole(), "") ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (usersDTO.getPostDTO() == null || Objects.equals(usersDTO.getPostDTO(), "") ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (usersDTO.getBannedDTO() == null || Objects.equals(usersDTO.getBannedDTO(), "") ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (usersDTO.getFlairDTO() == null || Objects.equals(usersDTO.getFlairDTO(), "") ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (usersDTO.getCommentDTO() == null || Objects.equals(usersDTO.getCommentDTO(), "") ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Users users = userService.save(usersDTO);
        return ResponseEntity.ok().body(users);

    }
}
