package com.RadioManagement.RadioManagement.Service;

import com.RadioManagement.RadioManagement.DTO.AuthRegRequest;
import com.RadioManagement.RadioManagement.Entity.AppUser;
import com.RadioManagement.RadioManagement.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
