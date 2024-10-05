package com.lnct.miniblog.controller;

import com.lnct.miniblog.model.User;
import com.lnct.miniblog.security.JwtTokenProvider;
import com.lnct.miniblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        // Encrypt the user password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user in the database
        User createdUser = userService.saveUser(user);

        // Generate the JWT token for the newly registered user
        String jwt = tokenProvider.generateToken(createdUser.getUsername());

        // Return a cleaned-up response without the password
        Map<String, Object> response = Map.of(
                "id", createdUser.getId(),
                "username", createdUser.getUsername(),
                "token", jwt
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        Optional<User> optionalUser = userService.findByUsername(username);
        if (optionalUser.isPresent() && passwordEncoder.matches(password, optionalUser.get().getPassword())) {
            String token = tokenProvider.generateToken(optionalUser.get().getUsername());
            return ResponseEntity.ok(Map.of("token", token));  // Return only the token
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed!");
    }

}
