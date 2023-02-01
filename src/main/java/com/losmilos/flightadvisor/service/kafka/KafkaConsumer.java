package com.losmilos.flightadvisor.service.kafka;

import com.losmilos.flightadvisor.model.dto.messaging.CommentDataMessage;
import com.losmilos.flightadvisor.model.mapper.CommentMapperImpl;
import com.losmilos.flightadvisor.service.CommentService;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class KafkaConsumer {

    private final CommentService commentService;

    private final CommentMapperImpl commentMapper;

    @Bean
    public Consumer<CommentDataMessage> comments() {
        return message -> {
            log.info("Comment message received {}", message);
            commentService.checkInappropriate(commentMapper.messageToDomain(message));
        };
    }

}
