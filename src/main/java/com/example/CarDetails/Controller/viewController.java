package com.example.CarDetails.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class viewController {

    @GetMapping("/loginpage")
    public String showLoginPage() {
        return "login"; // Returns the login.html view
    }

    @GetMapping("/signuppage")
    public String showSignupPage() {
        return "signup"; // Returns the signup.html view
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestHeader("Authorization") String token) {
        // Logic to invalidate the token or mark it unusable (if you're storing tokens somewhere)

        return ResponseEntity.ok("Logout successful");
    }
}
