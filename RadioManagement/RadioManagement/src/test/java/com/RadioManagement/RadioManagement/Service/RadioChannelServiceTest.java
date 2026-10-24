
package com.RadioManagement.RadioManagement.Service;

import com.RadioManagement.RadioManagement.DTO.AddChannelRequest;
import com.RadioManagement.RadioManagement.DTO.AddProgramRequest;
import com.RadioManagement.RadioManagement.DTO.UpdateFreqRequest;
import com.RadioManagement.RadioManagement.Entity.RadioChannel;
import com.RadioManagement.RadioManagement.Event.ChannelCreatedEvent;
import com.RadioManagement.RadioManagement.Exception.ChannelNotFoundException;
import com.RadioManagement.RadioManagement.Exception.DuplicateChannelException;
import com.RadioManagement.RadioManagement.Repository.RadioChannelRepository;
import com.RadioManagement.RadioManagement.Repository.RadioProgramRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RadioChannelServiceTest {

    @Mock
    private RadioChannelRepository radioChannelRepository;

    @Mock
    private RadioProgramRepository radioProgramRepository;

    @Mock
    private KafkaTemplate<String, ChannelCreatedEvent> kafkaTemplate;

    @InjectMocks
    private RadioChannelService radioChannelService;

    @Test
    void addChannel_whenChannelDoesNotExist_savesChannel() {
        AddChannelRequest request = new AddChannelRequest();
        request.setName("Blue FM");
        request.setGenre("Music");
        request.setFrequency(98.6);

        when(radioChannelRepository.findByName("Blue FM"))
                .thenReturn(Optional.empty());

        when(radioChannelRepository.save(any(RadioChannel.class)))
                .thenAnswer(invocation -> {
                    RadioChannel saved = invocation.getArgument(0);
                    saved.setId(1L);
                    return saved;
                });

        radioChannelService.addChannel(request);

        verify(radioChannelRepository).save(any(RadioChannel.class));
        verify(kafkaTemplate).send(
                eq("radio.channel.created"),
                any(ChannelCreatedEvent.class)
        );
    }

    @Test
    void addChannel_whenChannelAlreadyExists_throwsException() {
        AddChannelRequest request = new AddChannelRequest();
        request.setName("Blue FM");
        request.setGenre("Music");
        request.setFrequency(98.6);

        when(radioChannelRepository.findByName("Blue FM"))
                .thenReturn(Optional.of(new RadioChannel()));

        assertThrows(
                DuplicateChannelException.class,
                () -> radioChannelService.addChannel(request)
        );

        verify(radioChannelRepository, never()).save(any(RadioChannel.class));
        verifyNoInteractions(kafkaTemplate);
    }

    @Test
    void updateFrequency_whenChannelDoesNotExist_throwsException() {
        UpdateFreqRequest request = new UpdateFreqRequest();
        request.setId(999L);
        request.setNewFrequency(101.5);

        when(radioChannelRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                ChannelNotFoundException.class,
                () -> radioChannelService.updateFrequency(request)
        );

        verify(radioChannelRepository, never()).save(any(RadioChannel.class));
    }

    @Test
    void updateFrequency_whenChannelExists_savesUpdatedChannel() {
        RadioChannel channel = new RadioChannel();
        channel.setFrequency(98.6);

        UpdateFreqRequest request = new UpdateFreqRequest();
        request.setId(1L);
        request.setNewFrequency(101.5);

        when(radioChannelRepository.findById(1L))
                .thenReturn(Optional.of(channel));

        radioChannelService.updateFrequency(request);

        verify(radioChannelRepository).save(channel);
        assertEquals(101.5, channel.getFrequency(), 0.001);
    }

    @Test
    void addProgram_whenChannelDoesNotExist_throwsException() {
        AddProgramRequest request = new AddProgramRequest();
        request.setChannelId(999L);
        request.setTitle("Morning Show");
        request.setDescription("Morning music and news");

        when(radioChannelRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                ChannelNotFoundException.class,
                () -> radioChannelService.addProgram(request)
        );

        verify(radioProgramRepository, never()).save(any());
    }
}
