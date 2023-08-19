package com.ftn.reddit.controller;

import com.ftn.reddit.model.pretraga.ChangePasswordRequest;
import com.ftn.reddit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@CrossOrigin
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        boolean isOldPasswordValid = userService.verifyOldPassword(username, request.getOldPassword());

        if (!isOldPasswordValid) {
            return ResponseEntity.badRequest().body("Invalid old password");
        }
        userService.updatePassword(username, request.getNewPassword());
        return ResponseEntity.ok("Password changed successfully");
    }

}
