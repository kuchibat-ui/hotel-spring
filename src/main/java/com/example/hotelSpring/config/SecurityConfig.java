package com.example.hotelSpring.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authz->authz
                .requestMatchers("/register","/login","/").permitAll()
                .requestMatchers("/css/**","/js/**","/img/**").permitAll()
                .requestMatchers("/dashboard").hasRole("ADMIN")
                .anyRequest().authenticated()
        )
                .formLogin(form->form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard")
                        .permitAll())


                .logout(logout->logout.logoutSuccessUrl("/").permitAll());
        return http.build();
    }
}