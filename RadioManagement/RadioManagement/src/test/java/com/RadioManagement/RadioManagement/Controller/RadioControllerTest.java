
package com.RadioManagement.RadioManagement.Controller;

import com.RadioManagement.RadioManagement.DTO.AddChannelRequest;
import com.RadioManagement.RadioManagement.DTO.AddProgramRequest;
import com.RadioManagement.RadioManagement.DTO.UpdateFreqRequest;
import com.RadioManagement.RadioManagement.Service.RadioChannelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.RadioManagement.RadioManagement.Security.JwtUtil;
import com.RadioManagement.RadioManagement.Service.UserDetailsServices;
import org.springframework.cache.CacheManager;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RadioController.class)
class RadioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private RadioChannelService radioChannelService;

    @MockitoBean
    private UserDetailsServices userDetailsServices;

    @MockitoBean
    private CacheManager cacheManager;

    @Test
    void addChannel_returns201() throws Exception {
        mockMvc.perform(post("/channel/add")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Blue FM",
                                  "genre": "Music",
                                  "frequency": 98.6
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(content().string("Added the channel"));

        verify(radioChannelService).addChannel(any(AddChannelRequest.class));
    }

    @Test
    void addChannel_whenNameIsBlank_returns400() throws Exception {
        mockMvc.perform(post("/channel/add")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "",
                                  "genre": "Music",
                                  "frequency": 98.6
                                }
                                """))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(radioChannelService);
    }

    @Test
    void getChannels_returns200() throws Exception {
        when(radioChannelService.getChannels())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/channel/list"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(radioChannelService).getChannels();
    }

    @Test
    void searchChannels_returns200() throws Exception {
        when(radioChannelService.searchChannels("Blue"))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/channel/search")
                        .param("query", "Blue"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(radioChannelService).searchChannels("Blue");
    }

    @Test
    void updateFrequency_returns200() throws Exception {
        mockMvc.perform(put("/channel/batch-update-frequency")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 1,
                                  "newFrequency": 101.5
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Updated"));

        verify(radioChannelService).updateFrequency(
                any(UpdateFreqRequest.class));
    }

    @Test
    void addProgram_returns201() throws Exception {
        mockMvc.perform(post("/channel/program/add")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "Morning Show",
                                  "description": "Music and news",
                                  "channelId": 1
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message")
                        .value("Program added successfully"));

        verify(radioChannelService).addProgram(
                any(AddProgramRequest.class));
    }

    @Test
    void getPrograms_returns200() throws Exception {
        when(radioChannelService.getPrograms(1L))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/channel/program/listby")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(radioChannelService).getPrograms(1L);
    }
}
