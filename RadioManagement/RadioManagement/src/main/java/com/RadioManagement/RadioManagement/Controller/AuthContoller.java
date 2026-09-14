package com.RadioManagement.RadioManagement.Controller;

import com.RadioManagement.RadioManagement.DTO.AuthRegRequest;
import com.RadioManagement.RadioManagement.DTO.AuthRequest;
import com.RadioManagement.RadioManagement.DTO.AuthResponse;
import com.RadioManagement.RadioManagement.Security.JwtUtil;
import com.RadioManagement.RadioManagement.Service.AuthService;
import com.RadioManagement.RadioManagement.Service.UserDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthContoller {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsServices userDetailsServices;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );

            UserDetails user =
                    userDetailsServices.loadUserByUsername(authRequest.getUsername());

            String token = jwtUtil.generateToken(user);

            return ResponseEntity.ok(new AuthResponse(token, 200));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Not Authenticated");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRegRequest authRegRequest) {
        authService.register(authRegRequest);
        return ResponseEntity.status(201).body("User registered succesfully");
    }
}