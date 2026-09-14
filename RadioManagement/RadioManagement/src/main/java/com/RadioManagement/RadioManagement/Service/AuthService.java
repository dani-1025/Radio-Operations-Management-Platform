package com.RadioManagement.RadioManagement.Service;

import com.RadioManagement.RadioManagement.DTO.AuthRegRequest;
import com.RadioManagement.RadioManagement.DTO.AuthRequest;
import com.RadioManagement.RadioManagement.DTO.AuthResponse;
import com.RadioManagement.RadioManagement.Entity.AppUser;
import com.RadioManagement.RadioManagement.Repository.UserRepository;
import com.RadioManagement.RadioManagement.Security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServices userDetailsServices;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       UserDetailsServices userDetailsServices,
                       JwtUtil jwtUtil) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsServices = userDetailsServices;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse login(AuthRequest authRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        UserDetails user =
                userDetailsServices.loadUserByUsername(authRequest.getUsername());

        String token = jwtUtil.generateToken(user);

        return new AuthResponse(token, 200);
    }

    public void register(AuthRegRequest authRegRequest) {

        if (userRepository.findByUsername(authRegRequest.getUsername()).isPresent()) {
            throw new RuntimeException("User Exists");
        }

        AppUser appUser = new AppUser();

        appUser.setUsername(authRegRequest.getUsername());
        appUser.setPassword(passwordEncoder.encode(authRegRequest.getPassword()));
        appUser.setRole(authRegRequest.getRole());

        userRepository.save(appUser);
    }
}
