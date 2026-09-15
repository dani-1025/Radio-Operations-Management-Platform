package com.RadioManagement.RadioManagement.Controller;

import com.RadioManagement.RadioManagement.DTO.AddChannelRequest;
import com.RadioManagement.RadioManagement.DTO.AddProgramRequest;
import com.RadioManagement.RadioManagement.DTO.UpdateFreqRequest;
import com.RadioManagement.RadioManagement.Service.RadioChannelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/channel")
public class RadioController {

    private final RadioChannelService radioChannelService;

    public RadioController(RadioChannelService radioChannelService) {
        this.radioChannelService = radioChannelService;
    }

    // POST /channel/add
    @PostMapping("/add")
    public ResponseEntity<?> addChannel(
            @Valid @RequestBody AddChannelRequest request) {

        radioChannelService.addChannel(request);

        return ResponseEntity.status(201)
                .body("Added the channel");
    }

    // GET /channel/list
    @GetMapping("/list")
    public ResponseEntity<?> getChannels() {
        return ResponseEntity.ok(
                radioChannelService.getChannels()
        );
    }

    // GET /channel/search?query=TEXT
    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam("query") String query) {

        return ResponseEntity.ok(
                radioChannelService.searchChannels(query)
        );
    }

    // PUT /channel/batch-update-frequency
    @PutMapping("/batch-update-frequency")
    public ResponseEntity<?> updateFrequency(
            @Valid @RequestBody UpdateFreqRequest request) {

        radioChannelService.updateFrequency(request);

        return ResponseEntity.ok("Updated");
    }

    // POST /channel/program/add
    @PostMapping("/program/add")
    public ResponseEntity<?> addProgram(
           @Valid @RequestBody AddProgramRequest request) {

        radioChannelService.addProgram(request);

        return ResponseEntity.status(201)
                .body(Map.of("message", "Program added successfully"));
    }

    // GET /channel/program/listby?id=CHANNEL_ID
    @GetMapping("/program/listby")
    public ResponseEntity<?> getPrograms(
            @RequestParam("id") Long id) {

        return ResponseEntity.ok(
                radioChannelService.getPrograms(id)
        );
    }
}