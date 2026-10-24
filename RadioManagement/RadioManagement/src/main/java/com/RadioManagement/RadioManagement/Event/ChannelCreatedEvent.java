package com.RadioManagement.RadioManagement.Event;

public record ChannelCreatedEvent(
        Long channelId,
        String name,
        String genre,
        double frequency
) {}