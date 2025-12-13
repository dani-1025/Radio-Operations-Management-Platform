package com.RadioManagement.RadioManagement.Repository;

import com.RadioManagement.RadioManagement.Entity.RadioChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RadioChannelRepository  extends JpaRepository<RadioChannel,Integer> {
    List<RadioChannel> findByNameContainingIgnoreCaseOrGenreContainingIgnoreCase(String name,String genre);
}
