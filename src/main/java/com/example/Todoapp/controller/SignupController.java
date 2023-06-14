package com.example.Todoapp.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.example.Todoapp.auth.AuthRequest;
import com.example.Todoapp.auth.CustomResponse;
import com.example.Todoapp.entity.User;
import com.example.Todoapp.security.JwtTokenUtil;
import com.example.Todoapp.config.ApplicationSecurityConfig;
import com.example.Todoapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

@CrossOrigin(exposedHeaders = "Set-Cookie")
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class SignupController {

    private final AuthenticationManager authManager;

    private  final  ApplicationSecurityConfig appSec;

    private final  UserService userService;

    private final  JwtTokenUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        try {
            User user = new User();
            String username = request.getUsername();
            user.setUsername(request.getUsername());
            userService.checkIfUserExist(username);
            user.setPassword(appSec.passwordEncoder().encode(request.getPassword()));
            userService.saveUser(user);
            CustomResponse response = new CustomResponse("user successfully registered!");
            return ResponseEntity.ok().body(response);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}