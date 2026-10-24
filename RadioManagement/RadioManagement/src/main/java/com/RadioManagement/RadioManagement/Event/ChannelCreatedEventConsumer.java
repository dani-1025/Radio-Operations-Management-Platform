
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

        if (event == null || event.channelId() == null) {
            throw new IllegalArgumentException(
                    "Invalid ChannelCreatedEvent: channel ID is required"
            );
        }

        System.out.println("RECEIVED KAFKA EVENT: " + event);
    }
}
