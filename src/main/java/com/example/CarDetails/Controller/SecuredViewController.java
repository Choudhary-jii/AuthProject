package com.example.CarDetails.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/protected")
public class SecuredViewController {

    @GetMapping("/user-homepage")
    public String userHome() {
        return "user-homepage"; // Returns the signup.html view
    }

    @GetMapping("/admin-homepage")
    public String adminHome() {
        return "admin-homepage"; // Returns the signup.html view
    }
}
