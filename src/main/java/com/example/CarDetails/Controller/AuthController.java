package com.example.CarDetails.Controller;

import com.example.CarDetails.Modal.Users;
import com.example.CarDetails.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/hi")
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