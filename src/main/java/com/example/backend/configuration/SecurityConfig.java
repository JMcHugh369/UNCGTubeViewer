package com.example.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .anyRequest().permitAll() // Allow all requests without authentication
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login") // Specify your custom login page
                                .successHandler(new AuthenticationSuccessHandler() {

                                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                                        String username = userDetails.getUsername();
                                        response.sendRedirect("/users/home/" + username);
                                    }
                                })
                                .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // Disable CSRF protection

        return http.build();
    }
}