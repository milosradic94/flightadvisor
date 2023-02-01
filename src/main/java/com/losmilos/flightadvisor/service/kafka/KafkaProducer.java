package com.losmilos.flightadvisor.service.kafka;

import com.losmilos.flightadvisor.model.dto.messaging.CommentDataMessage;
import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KafkaProducer {

    private final StreamBridge streamBridge;

    @Async
    public void produceCommentDetails(CommentDataMessage message) {
      streamBridge.send("comments-out-0", MessageBuilder.withPayload(message)
          .setHeader(KafkaHeaders.MESSAGE_KEY, message.getId())
          .build());
    }

}
