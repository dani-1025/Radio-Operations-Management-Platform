package com.RadioManagement.RadioManagement.Event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ChannelCreatedEventConsumer {

    @KafkaListener(
            topics = "radio.channel.created",
            groupId = "radio-operations-group"
    )
    public void consume(ChannelCreatedEvent event) {
        System.out.println(
                "✅ RECEIVED KAFKA EVENT: " + event
        );
    }
}