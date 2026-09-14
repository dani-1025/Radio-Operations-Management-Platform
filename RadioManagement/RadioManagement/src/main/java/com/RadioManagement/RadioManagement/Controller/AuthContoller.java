package com.RadioManagement.RadioManagement.Controller;

import com.RadioManagement.RadioManagement.DTO.AuthRegRequest;
import com.RadioManagement.RadioManagement.DTO.AuthRequest;
import com.RadioManagement.RadioManagement.DTO.AuthResponse;
import com.RadioManagement.RadioManagement.Entity.AppUser;
import com.RadioManagement.RadioManagement.Repository.UserRepository;
import com.RadioManagement.RadioManagement.Security.JwtUtil;
import com.RadioManagement.RadioManagement.Service.UserDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        try{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (authRequest.getUsername(),authRequest.getPassword()));
        UserDetails user = userDetailsServices.loadUserByUsername(authRequest.getUsername());
        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token,200));

    } catch (Exception e) {
            return ResponseEntity.badRequest().body("Not Authenticated");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?>  register(@RequestBody AuthRegRequest authRegRequest){
       if(userRepository.findByUsername(authRegRequest.getUsername()).isPresent())
            return ResponseEntity.badRequest().body("User Exists");
       String user= authRegRequest.getUsername();
       String pass= authRegRequest.getPassword();
       String role= authRegRequest.getRole();
       AppUser appUser= new AppUser();
        appUser.setUsername(user);
        appUser.setPassword(passwordEncoder.encode(pass));
        appUser.setRole(role);
        userRepository.save(appUser);
        return ResponseEntity.status(201).body("User registered succesfully");
    }
}
