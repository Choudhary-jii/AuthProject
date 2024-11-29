package com.example.CarDetails.Controller;

import com.example.CarDetails.Modal.Users;
import com.example.CarDetails.Repository.UserRepository;
import com.example.CarDetails.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody Users user) {
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username or email already exists, try login");
        }
        user.setRole(Set.of("USER"));
        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/hi")
    public String Hello(){
        return "hello";
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody Users user) {
        String jwtToken = userService.authenticateUser(user);
        if (jwtToken != null) {
            return ResponseEntity.ok(jwtToken);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials, or try signing up!");
        }
    }

}





//    @PostMapping("/login")
//    public ResponseEntity<Void> loginUser(@RequestBody Users user, HttpServletResponse response) {
//        String token = userService.authenticateUser(user);
//        if (token != null) {
//            response.setHeader("Authorization", "Bearer " + token);
//
//            try {
//                List<String> roles = user.getRole();
//                if (roles.contains("ADMIN")) {
//                    response.sendRedirect("/protected/admin-homepage");
//                } else if (roles.contains("USER")) {
//                    response.sendRedirect("/protected/user-homepage");
//                } else {
//                    return ResponseEntity.status(403).build();
//                }
//            } catch (IOException e) {
//                return ResponseEntity.status(500).build();
//            }
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.status(401).build();
//        }
//    }