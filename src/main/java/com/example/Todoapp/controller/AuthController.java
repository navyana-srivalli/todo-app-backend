package com.example.Todoapp.controller;
import org.springframework.web.bind.annotation.*;

import com.example.Todoapp.auth.AuthRequest;
import com.example.Todoapp.auth.AuthResponse;
import com.example.Todoapp.auth.CustomResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import com.example.Todoapp.entity.User;
import com.example.Todoapp.security.JwtTokenUtil;
import com.example.Todoapp.config.ApplicationSecurityConfig;
import com.example.Todoapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@CrossOrigin(exposedHeaders = "Set-Cookie")
@RequestMapping("/api")
@RestController
public class AuthController{

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    ApplicationSecurityConfig appSec;


    @Autowired
    UserService userService;

    @Autowired
    JwtTokenUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request, HttpServletResponse response) {
        try {
            Authentication auth = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
            Authentication authentication = authManager.authenticate(auth);
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponse responseBody = new AuthResponse(user.getUsername(), accessToken);

            if (accessToken != null) {
                Cookie accessTokenCookie = new Cookie("access-token", accessToken);
                System.out.println(accessTokenCookie.getValue());
                accessTokenCookie.setPath("/");
                accessTokenCookie.setMaxAge(3600);
                response.addCookie(accessTokenCookie);
            }

            return ResponseEntity.ok().body(responseBody);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        try {
            User user = new User();
            String username = request.getUsername();
            user.setUsername(request.getUsername());
            if(userService.find(username) != null){
                System.out.println("Already present");
                CustomResponse response = new CustomResponse("User already present");
                return ResponseEntity.status(400).body(response);
            }else{
                user.setPassword(appSec.passwordEncoder().encode(request.getPassword()));
                userService.saveUser(user);
                CustomResponse response = new CustomResponse("user successfully registered!");
                return ResponseEntity.ok().body(response);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}