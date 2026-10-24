package com.RadioManagement.RadioManagement.Service;

import com.RadioManagement.RadioManagement.DTO.AddChannelRequest;
import com.RadioManagement.RadioManagement.DTO.AddProgramRequest;
import com.RadioManagement.RadioManagement.DTO.UpdateFreqRequest;
import com.RadioManagement.RadioManagement.Entity.RadioChannel;
import com.RadioManagement.RadioManagement.Entity.RadioProgram;

import com.RadioManagement.RadioManagement.Exception.ChannelNotFoundException;
import com.RadioManagement.RadioManagement.Exception.DuplicateChannelException;
import com.RadioManagement.RadioManagement.Repository.RadioChannelRepository;
import com.RadioManagement.RadioManagement.Repository.RadioProgramRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import com.RadioManagement.RadioManagement.Event.ChannelCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import java.util.List;
import java.util.Optional;

@Service
public class RadioChannelService {

    private final RadioChannelRepository radioChannelRepository;
    private final RadioProgramRepository radioProgramRepository;
    private final KafkaTemplate<String, ChannelCreatedEvent> kafkaTemplate;

    public RadioChannelService(
            RadioChannelRepository radioChannelRepository,
            RadioProgramRepository radioProgramRepository,
            KafkaTemplate<String, ChannelCreatedEvent> kafkaTemplate) {

        this.radioChannelRepository = radioChannelRepository;
        this.radioProgramRepository = radioProgramRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    // Add channel
    @CacheEvict(value = {"channels", "channelSearch"}, allEntries = true)
    public void addChannel(AddChannelRequest request) {

        if (radioChannelRepository.findByName(request.getName()).isPresent()) {
            throw new DuplicateChannelException(
                    "Channel with name '" + request.getName() + "' already exists"
            );
        }

        RadioChannel channel = new RadioChannel();
        channel.setGenre(request.getGenre());
        channel.setName(request.getName());
        channel.setFrequency(request.getFrequency());

        RadioChannel savedChannel = radioChannelRepository.save(channel);

        ChannelCreatedEvent event = new ChannelCreatedEvent(
                savedChannel.getId(),
                savedChannel.getName(),
                savedChannel.getGenre(),
                savedChannel.getFrequency()
        );

        kafkaTemplate.send("radio.channel.created", event);
    }

    // Get all channels
    @Cacheable("channels")
    public List<RadioChannel> getChannels() {
        return radioChannelRepository.findAll();
    }

    // Search channels
    @Cacheable(value = "channelSearch", key = "#query.toLowerCase()")
    public List<RadioChannel> searchChannels(String query) {
        return radioChannelRepository
                .findByNameContainingIgnoreCaseOrGenreContainingIgnoreCase(query, query);
    }

    // Update channel frequency
    @CacheEvict(value = {"channels", "channelSearch"}, allEntries = true)
    public void updateFrequency(UpdateFreqRequest request) {
        Optional<RadioChannel> radio =
                radioChannelRepository.findById(request.getId());

        if (radio.isEmpty()) {
            throw new ChannelNotFoundException(
                    "No channel with id " + request.getId()
            );
        }
        RadioChannel channel = radio.get();
        channel.setFrequency(request.getNewFrequency());
        radioChannelRepository.save(channel);
    }

    // Add program
    public void addProgram(AddProgramRequest request) {

        Optional<RadioChannel> channelOpt =
                radioChannelRepository.findById(request.getChannelId());

        if (channelOpt.isEmpty()) {
            throw new ChannelNotFoundException(
                    "No channel with id " + request.getChannelId()
            );
        }

        RadioProgram program = new RadioProgram();
        program.setTitle(request.getTitle());
        program.setDescription(request.getDescription());
        program.setChannel(channelOpt.get());

        radioProgramRepository.save(program);
    }

    // Get programs by channel
    public List<RadioProgram> getPrograms(Long channelId) {
        return radioProgramRepository.findByChannelId(channelId);
    }
}