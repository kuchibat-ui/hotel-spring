package com.example.hotelSpring.controller;

import com.example.hotelSpring.entity.Client;
import com.example.hotelSpring.entity.User;
import com.example.hotelSpring.repository.AuthRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {
    private AuthRepository authRepository;
private final PasswordEncoder passwordEncoder;

    public AuthController(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {
        Optional<User> user = authRepository.findByUsernameAndPassword(username, password);
        if (user.isPresent()) {
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "неверные учетные данные");
            return "/login";
        }
    }


    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email)
    {
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(email);
        authRepository.save(user);
        return "redirect:/login";
    }

}
