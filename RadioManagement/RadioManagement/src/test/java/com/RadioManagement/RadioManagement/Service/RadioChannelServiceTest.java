
package com.RadioManagement.RadioManagement.Service;

import com.RadioManagement.RadioManagement.DTO.AddChannelRequest;
import com.RadioManagement.RadioManagement.Entity.RadioChannel;
import com.RadioManagement.RadioManagement.Exception.DuplicateChannelException;
import com.RadioManagement.RadioManagement.Repository.RadioChannelRepository;
import com.RadioManagement.RadioManagement.Repository.RadioProgramRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RadioChannelServiceTest {

    @Mock
    private RadioChannelRepository radioChannelRepository;

    @Mock
    private RadioProgramRepository radioProgramRepository;

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

        radioChannelService.addChannel(request);

        verify(radioChannelRepository).save(any(RadioChannel.class));
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
    }
}
