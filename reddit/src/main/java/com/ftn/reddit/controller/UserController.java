package com.ftn.reddit.controller;

import com.ftn.reddit.DTO.CommunityDTO;
import com.ftn.reddit.DTO.UsersDTO;
import com.ftn.reddit.model.Community;
import com.ftn.reddit.model.Users;
import com.ftn.reddit.model.pretraga.ChangePasswordRequest;
import com.ftn.reddit.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("api/user")
@CrossOrigin
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private ResourceLoader resourceLoader;

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordRequest request,
            HttpSession session) {
        //Users loggedInUser = (Users) session.getAttribute("loggedUser");
        Users loggedInUser = userService.findById(5);
        boolean isOldPasswordValid = userService.verifyOldPassword(loggedInUser.getUsername(), request.getOldPassword());

        if (!isOldPasswordValid) {
            return ResponseEntity.badRequest().body("Invalid old password");
        }
        userService.updatePassword(loggedInUser.getUsername(), request.getNewPassword());
        return ResponseEntity.ok("Password changed successfully");
    }

    @GetMapping("/all")
    public List<Users> getAllUsers() {
        var users = userService.findAll();
        for (Users user : users) {
            if (user.getAvatar() != null) {
                try {
                    byte[] fileBytes = Files.readAllBytes(Paths.get(user.getAvatar()));
                    user.setAvatar("data:image/png;base64, " + Base64.getEncoder().encodeToString(fileBytes));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return users;
    }

    @PostMapping("/switch-role/{user_id}")
    public ResponseEntity<String> switchUserRoleToUser(@PathVariable Integer user_id) {

        boolean success = userService.switchUserRoleToUser(user_id);

        if (success) {
            return ResponseEntity.ok("User role switched to USER successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to switch user role.");
        }
    }


    @GetMapping("/loggedin")
    public ResponseEntity<Users> getLoggedInUserProfile(HttpSession httpRequest) {
        //Users loggedInUser = (Users)  httpRequest.getAttribute("loggedUser");
        Users loggedInUser = userService.findById(5);
        if (loggedInUser != null) {
            return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/suspend-user/{user_id}")
    public ResponseEntity<String> suspendUser(@PathVariable Integer user_id) {
        boolean success = userService.suspendUser(user_id);

        if (success) {
            return ResponseEntity.ok("User suspended successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to suspend user.");
        }
    }

    @PutMapping("/return/{user_id}")
    public ResponseEntity<String> returnUser(@PathVariable Integer user_id) {
        boolean success = userService.returnUser(user_id);

        if (success) {
            return ResponseEntity.ok("User returned successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to returned user.");
        }
    }


    @PutMapping("/again/{user_id}")
    public ResponseEntity<Users> updateUser(
            @PathVariable Integer user_id,
            @RequestPart("username") String username,
            @RequestPart("email") String email,
            @RequestParam(value = "avatar", required = false) MultipartFile avatarFile) throws IOException {

        Users existingCommunity = userService.findById(user_id);
        if (existingCommunity == null) {
            return ResponseEntity.notFound().build();
        }

        if (!username.equals(existingCommunity.getUsername())) {
            existingCommunity.setUsername(username);
        }

        if (!email.equals(existingCommunity.getEmail())) {
            existingCommunity.setEmail(email);
        }

        String filePath = null;
        if (avatarFile != null && !avatarFile.isEmpty()) {
            filePath = saveFile(avatarFile);
            existingCommunity.setAvatar(filePath);
        }

        Users updated = userService.save(existingCommunity);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<Users> getUserById(@PathVariable Integer user_id) {
        Users community = userService.findById(user_id);
        if (community == null && community.isSuspended() == true) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(community, HttpStatus.OK);
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
