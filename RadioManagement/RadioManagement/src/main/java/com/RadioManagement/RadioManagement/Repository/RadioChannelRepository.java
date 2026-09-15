package com.RadioManagement.RadioManagement.Repository;

import com.RadioManagement.RadioManagement.Entity.RadioChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RadioChannelRepository extends JpaRepository<RadioChannel, Long> {

    List<RadioChannel> findByNameContainingIgnoreCaseOrGenreContainingIgnoreCase(
            String name,
            String genre
    );

    Optional<RadioChannel> findByName(String name);
}