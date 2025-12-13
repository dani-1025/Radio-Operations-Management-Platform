package com.RadioManagement.RadioManagement.Controller;

import com.RadioManagement.RadioManagement.DTO.AddChannelRequest;
import com.RadioManagement.RadioManagement.DTO.AddProgramRequest;
import com.RadioManagement.RadioManagement.DTO.UpdateFreqRequest;
import com.RadioManagement.RadioManagement.Entity.RadioChannel;
import com.RadioManagement.RadioManagement.Entity.RadioProgram;
import com.RadioManagement.RadioManagement.Repository.RadioChannelRepository;
import com.RadioManagement.RadioManagement.Repository.RadioProgramRepository;
import com.RadioManagement.RadioManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/channel")
public class RadioController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RadioChannelRepository radioChannelRepository;
    @Autowired
    RadioProgramRepository radioProgramRepository;

    //post addchannel
    @PostMapping("/add")
    public ResponseEntity<?> addChannel(@RequestBody AddChannelRequest addChannelRequest){
        if(userRepository.findByUsername(addChannelRequest.getName()).isPresent())
            return ResponseEntity.badRequest().body(Map.of("message","Channel already exists"));
        if(addChannelRequest.getGenre()==null ||addChannelRequest.getName()==null)
            return ResponseEntity.badRequest().body(Map.of("message","Invalid Data"));
        RadioChannel channel= new RadioChannel();
        channel.setGenre(addChannelRequest.getGenre());
        channel.setName(addChannelRequest.getName());
        channel.setFrequency(addChannelRequest.getFrequency());
        radioChannelRepository.save(channel);
        return ResponseEntity.status(201).body("Added the channel ");
    }

    //Getchannel
    @GetMapping("/list")
    public ResponseEntity<?> getChannels(){
        List<RadioChannel> channels = new ArrayList<>();
        channels=radioChannelRepository.findAll();
            return (channels.isEmpty())?ResponseEntity.badRequest().body("Not exists"):ResponseEntity.ok(channels.stream().collect(Collectors.toList()));
    }

    //GET /channel/search?query=TEXT
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("query") String query){
        List<RadioChannel> channels= new ArrayList<>();
        channels=radioChannelRepository.findByNameContainingIgnoreCaseOrGenreContainingIgnoreCase(query,query);
        return (channels.isEmpty())?ResponseEntity.badRequest().body("Not exists"):ResponseEntity.ok(channels);
    }

    //PUT /channel/batch-update-frequency
    @PutMapping("/batch-update-frequency")
    public ResponseEntity<?> updateFrequency(@RequestBody UpdateFreqRequest updateFreqRequest){
        Optional<RadioChannel> radio=radioChannelRepository.findById(updateFreqRequest.getId());
        if(radio.isPresent()) {
            RadioChannel channel = radio.get();
            channel.setFrequency(updateFreqRequest.getNewFrequency());
            radioChannelRepository.save(channel);
            return ResponseEntity.status(201).body("Updated");
        }else{
            return ResponseEntity.badRequest().body("No Channel with this id");
        }
    }

    //addprogram
    @PostMapping("/program/add")
    public ResponseEntity<?> addProgram(@RequestBody AddProgramRequest request) {

        // 1. Validate channel existence
        Optional<RadioChannel> channelOpt = radioChannelRepository.findById(request.getChannelId());
        if (channelOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Channel not found"));
        }

        RadioChannel channel = channelOpt.get();

        // 2. Create Program
        RadioProgram program = new RadioProgram();
        program.setTitle(request.getTitle());
        program.setDescription(request.getDescription());
        program.setChannel(channel);

        // 3. Save to database
        radioProgramRepository.save(program);

        return ResponseEntity.status(201).body(Map.of("message", "Program added successfully"));
    }


    // GET /program/list-by-channel/{channelId}
    @GetMapping("/program/listby")
    public ResponseEntity<?> getPrograms(@RequestParam("id") Integer id){

        List<RadioProgram> program = radioProgramRepository.findByChannelId(id);
        return(program.isEmpty())?ResponseEntity.badRequest().body("Not exists"):ResponseEntity.status(201).body(program);
    }
}
