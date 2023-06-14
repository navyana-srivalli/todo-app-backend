package com.example.Todoapp.filter;
import com.example.Todoapp.auth.AuthResponse;
import com.example.Todoapp.entity.User;
import com.example.Todoapp.security.JwtTokenUtil;
import com.example.Todoapp.service.CurrentUserContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.Todoapp.auth.AuthRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;


public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper objectMapper = new ObjectMapper();
    private final AuthenticationManager authManager;


    private final JwtTokenUtil jwtUtil;



    public LoginFilter(AuthenticationManager authManager, JwtTokenUtil jwtUtil1) {
        super(new AntPathRequestMatcher("/api/login/"));
        this.authManager = authManager;
        this.jwtUtil = jwtUtil1;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        Authentication auth = null;
        BufferedReader reader = request.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        reader.close();
        AuthRequest authRequest = objectMapper.readValue(requestBody.toString(), AuthRequest.class);

        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        try {
            auth = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authManager.authenticate(auth);
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);;


            if (accessToken != null) {
                Cookie accessTokenCookie = new Cookie("access-token", accessToken);
                System.out.println(accessTokenCookie.getValue());
                accessTokenCookie.setPath("/");
                accessTokenCookie.setMaxAge(3600);
                response.addCookie(accessTokenCookie);
            }
            CurrentUserContext.setCurrentUser(username);


            response.setStatus(HttpServletResponse.SC_OK);
        } catch (BadCredentialsException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return  auth;
    }
}