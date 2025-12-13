package com.RadioManagement.RadioManagement.Repository;

import com.RadioManagement.RadioManagement.Entity.RadioProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RadioProgramRepository  extends JpaRepository<RadioProgram,Integer> {
    List<RadioProgram> findByChannelId(Integer channelId);
}
