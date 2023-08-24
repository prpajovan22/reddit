package com.ftn.reddit.controller;

import com.ftn.reddit.DTO.LoginDTO;
import com.ftn.reddit.DTO.UsersDTO;
import com.ftn.reddit.auth.AuthenticationRequest;
import com.ftn.reddit.auth.AuthenticationResponse;
import com.ftn.reddit.auth.AuthenticationService;
import com.ftn.reddit.model.UserRole;
import com.ftn.reddit.model.Users;
import com.ftn.reddit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Objects;

@RestController
@RequestMapping("api/login")
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/signIn")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            AuthenticationResponse response =  authenticationService.generateToken(request);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (AuthenticationException e) {
            // Authentication failed
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<UsersDTO> register(@RequestParam("username") String username,@RequestParam("password") String password,
                                             @RequestParam("email") String email,@RequestParam("description") String description,
                                             @RequestParam("displayName") String displayName,@RequestParam("userRole") UserRole userRole,
                                             @RequestParam(value = "avatar", required = false) MultipartFile avatarFile
    ) throws IOException {


        if (username == null || Objects.equals(username, "")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (password == null || Objects.equals(password, "")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (email == null || Objects.equals(email, "")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String filePath = null;
        if (avatarFile != null && !avatarFile.isEmpty()) {
            filePath = saveFile(avatarFile);
        }

        if (description == null || Objects.equals(description, "")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (displayName == null || Objects.equals(displayName, "")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (userRole == null || Objects.equals(userRole, "")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UsersDTO usersDTO = new UsersDTO();
        LocalDate registrationDate = LocalDate.now();
        usersDTO.setRegistrationDate(registrationDate);
        usersDTO.setPassword(passwordEncoder.encode(password));
        usersDTO.setAvatar(filePath);
        usersDTO.setUsername(username);
        usersDTO.setEmail(email);
        usersDTO.setUserRole(userRole);
        usersDTO.setDescription(description);
        usersDTO.setDisplayName(displayName);
        Users users = userService.save(usersDTO.ToUsersEntity());
        usersDTO.setUser_id(users.getUser_id());
        return ResponseEntity.ok().body(usersDTO);

    }


        private String getResourcesFolderPath() {
            Resource resource = resourceLoader.getResource("classpath:");
            try {
                return resource.getFile().getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        private String saveFile(MultipartFile file) throws IOException {
            String resourcesFolderPath = getResourcesFolderPath();
            if (resourcesFolderPath != null) {
                Path filePath = Paths.get(resourcesFolderPath, "images", file.getOriginalFilename());
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                return filePath.toString();
            } else {
                throw new IOException("Resources folder path is null.");
            }
        }
}
