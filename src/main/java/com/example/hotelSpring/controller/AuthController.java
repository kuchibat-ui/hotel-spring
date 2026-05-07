package com.example.hotelSpring.controller;


import com.example.hotelSpring.entity.User;
import com.example.hotelSpring.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;


@Controller
public class AuthController {
    private AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AuthController(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {
        System.out.println("попытка входа");
        Optional<User> userOpt = authRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            System.out.println("пользователь есть в БД");
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                System.out.println(user.getPassword());
                return "redirect:/dashboard";
            }
        }
        System.out.println("------------");
            model.addAttribute("error", "неверные учетные данные");
            return "redirect:/user-error";
        }



@GetMapping("/register")
public String showRegisterForm() {
    return "register";
}

@PostMapping("/register")
public String register(
                       @RequestParam String username,
                       @RequestParam String password,
                       @RequestParam String email) {
        User userNew = new User(username,passwordEncoder.encode(password),email);

    authRepository.save(userNew);
    return "redirect:/login";
}

}
