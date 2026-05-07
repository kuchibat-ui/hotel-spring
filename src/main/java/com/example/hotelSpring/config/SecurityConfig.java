package com.example.hotelSpring.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SecurityConfig.class);

    public SecurityConfig(){
        log.info("succses");
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(authz->authz
                .requestMatchers("/","/register").permitAll()
                .requestMatchers("/css/**","/js/**","/img/**").permitAll()
                .requestMatchers("/dashboard").hasRole("ADMIN")
                .anyRequest().authenticated()
        )
                .formLogin(form->form
                        .loginPage("/login")
                        .usernameParameter("username")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/dashboard",true)
                        .failureUrl("/user-error").permitAll()
                        .permitAll())


                .logout(logout->logout.logoutSuccessUrl("/").permitAll());


        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}