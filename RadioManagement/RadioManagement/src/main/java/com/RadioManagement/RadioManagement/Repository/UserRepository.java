package com.RadioManagement.RadioManagement.Repository;

import com.RadioManagement.RadioManagement.Entity.AppUser;
import com.RadioManagement.RadioManagement.Entity.RadioChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser,Integer> {
    Optional<AppUser> findByUsername(String Username);
}
