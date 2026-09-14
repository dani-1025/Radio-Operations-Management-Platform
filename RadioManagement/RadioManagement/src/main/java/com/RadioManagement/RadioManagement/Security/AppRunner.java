package com.RadioManagement.RadioManagement.Security;

import com.RadioManagement.RadioManagement.Entity.AppUser;
import com.RadioManagement.RadioManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {

    private final UserRepository userRepo;

    public AppRunner(UserRepository userRepository) {
        this.userRepo = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        AppUser appUser= new AppUser();
        appUser.setUsername("Daniel");
        appUser.setPassword("test");
        appUser.setRole("ADMIN");

        AppUser appUser1= new AppUser();
        appUser1.setUsername("Nikki");
        appUser1.setPassword("tests");
        appUser1.setRole("MEMBER");



        userRepo.save(appUser);
        userRepo.save(appUser1);

        System.out.println(appUser.getUsername());

    }
}
