package com.example.CarDetails.Controller;

import com.example.CarDetails.Modal.Users;
import com.example.CarDetails.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin")
public class adminController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;



    @Autowired
    public adminController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }


    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<Users> all = userService.getAll();
        if (all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<String> registerUser(@RequestBody Users user) {
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username or email already exists, try different credentials");
        }
        user.setRole(Set.of("ADMIN"));
        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

}
