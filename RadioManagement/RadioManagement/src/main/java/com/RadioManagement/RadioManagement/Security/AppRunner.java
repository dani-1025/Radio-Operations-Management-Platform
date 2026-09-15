package com.RadioManagement.RadioManagement.Security;

import com.RadioManagement.RadioManagement.Entity.AppUser;
import com.RadioManagement.RadioManagement.Repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public AppRunner(UserRepository userRepository,
                     PasswordEncoder passwordEncoder) {
        this.userRepo = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {

        createUserIfNotExists("Daniel", "test", "ADMIN");
        createUserIfNotExists("Nikki", "tests", "MEMBER");
    }

    private void createUserIfNotExists(String username,
                                       String password,
                                       String role) {

        if (userRepo.findByUsername(username).isPresent()) {
            return;
        }

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        userRepo.save(user);
    }
}