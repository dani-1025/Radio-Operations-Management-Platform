package com.RadioManagement.RadioManagement.Controller;

import com.RadioManagement.RadioManagement.DTO.AuthRegRequest;
import com.RadioManagement.RadioManagement.DTO.AuthRequest;
import com.RadioManagement.RadioManagement.DTO.AuthResponse;
import com.RadioManagement.RadioManagement.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthContoller {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            AuthResponse response = authService.login(authRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Not Authenticated");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid  @RequestBody AuthRegRequest authRegRequest) {
        authService.register(authRegRequest);
        return ResponseEntity.status(201).body("User registered succesfully");
    }
}