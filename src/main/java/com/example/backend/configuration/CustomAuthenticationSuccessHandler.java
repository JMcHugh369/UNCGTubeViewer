package com.example.backend.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        // Get the authenticated user's username
        String username = authentication.getName();

        // Redirect to the user-specific home page
        response.sendRedirect("/users/home/" + username);
    }

    @Override
    public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Authentication authentication) throws IOException, jakarta.servlet.ServletException {
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication)
            throws IOException, ServletException {

            // Get the authenticated user's username
            String username = authentication.getName();

            // Redirect to the user-specific home page
            response.sendRedirect("/users/home/" + username);
    }
}
