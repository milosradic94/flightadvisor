package com.losmilos.flightadvisor.listener;

import com.losmilos.flightadvisor.event.CommentUpsertedEvent;
import com.losmilos.flightadvisor.model.mapper.CommentMapperImpl;
import com.losmilos.flightadvisor.service.kafka.KafkaProducer;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
public class CommentUpsertedListener {

    private final KafkaProducer kafkaProducer;

    private final CommentMapperImpl commentMapper;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEvent(final CommentUpsertedEvent commentUpsertedEvent) {
        kafkaProducer.produceCommentDetails(
            commentMapper.domainToMessage(commentUpsertedEvent.getComment())
        );
    }

}
