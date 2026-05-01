package com.example.hotelSpring.controller;

import com.example.hotelSpring.entity.Client;
import com.example.hotelSpring.entity.User;
import com.example.hotelSpring.repository.AuthRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {
    private AuthRepository authRepository;

    public AuthController(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

@GetMapping("/login")
public String showLoginForm(){
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
//@GetMapping("/dashboard")
//        public String dashboard(){
//        return "dashboard";
//    }



}
//    @PostMapping("dashboard/{username}/{password}")
//    public String findUser(@PathVariable String username,@PathVariable String password, Model model){
//       Optional<User> user = authRepository.findByUsernameAndPassword(username,password);
//       if (user.isPresent()){
//           return "/dashboard";
//       }
//            model.addAttribute("error","неверные учетные данные");
//            return "/login";
//    }

}
