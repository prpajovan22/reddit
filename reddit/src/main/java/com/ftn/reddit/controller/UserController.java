package com.ftn.reddit.controller;

import com.ftn.reddit.DTO.UsersDTO;
import com.ftn.reddit.model.Users;
import com.ftn.reddit.model.pretraga.ChangePasswordRequest;
import com.ftn.reddit.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@CrossOrigin
public class UserController {


    @Autowired
    private UserService userService;

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
        return userService.findAll();
    }

    @PostMapping("/switch-role/{userId}")
    public ResponseEntity<String> switchUserRoleToUser(@PathVariable Integer user_id) {

        boolean success = userService.switchUserRoleToUser(user_id);

        if (success) {
            return ResponseEntity.ok("User role switched to USER successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to switch user role.");
        }
    }


    @GetMapping("/loggedin")
    public Users getLoggedInUserProfile(HttpServletRequest request) {
        Users loggedInUser = (Users)  request.getSession().getAttribute("loggedUser");

        if (loggedInUser != null) {
            return loggedInUser;
        } else {
            return null;
        }
    }

}
