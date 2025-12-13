package com.RadioManagement.RadioManagement.Service;

import com.RadioManagement.RadioManagement.Entity.AppUser;
import com.RadioManagement.RadioManagement.Repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServices implements UserDetailsService, UserDetailsPasswordService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails updatePassword(UserDetails user, @Nullable String newPassword) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user1 = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not Found"));
        return User
                .builder()
                .username(user1.getUsername())
                .password(user1.getPassword())
                .roles(user1.getRole()).build();
    }
}
