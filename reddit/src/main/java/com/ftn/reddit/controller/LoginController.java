package com.ftn.reddit.controller;

import com.ftn.reddit.DTO.LoginDTO;
import com.ftn.reddit.DTO.UsersDTO;
import com.ftn.reddit.auth.AuthenticationRequest;
import com.ftn.reddit.auth.AuthenticationResponse;
import com.ftn.reddit.auth.AuthenticationService;
import com.ftn.reddit.model.Users;
import com.ftn.reddit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Objects;

@RestController
@RequestMapping("api/login")
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        AuthenticationResponse response = authenticationService.authenticate(request);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<UsersDTO> register(@RequestBody UsersDTO usersDTO) {
        if (usersDTO.getUsername() == null || Objects.equals(usersDTO.getUsername(), "")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (usersDTO.getPassword() == null || Objects.equals(usersDTO.getPassword(), "")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (usersDTO.getEmail() == null || Objects.equals(usersDTO.getEmail(), "")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (usersDTO.getAvatar() == null || Objects.equals(usersDTO.getAvatar(), "")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (usersDTO.getDescription() == null || Objects.equals(usersDTO.getDescription(), "")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (usersDTO.getDisplayName() == null || Objects.equals(usersDTO.getDisplayName(), "")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (usersDTO.getUserRole() == null || Objects.equals(usersDTO.getUserRole(), "")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LocalDate registrationDate = LocalDate.now();
        usersDTO.setRegistrationDate(registrationDate);
        Users users = userService.save(usersDTO.ToUsersEntity());
        usersDTO.setUser_id(users.getUser_id());
        return ResponseEntity.ok().body(usersDTO);

    }
}
