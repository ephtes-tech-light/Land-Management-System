package com.transfer.request.event;

import com.land.events.TransferApprovedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class TransferEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final static String TOPIC="transfer_event";

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void transferApproveEventPublish(TransferApprovedEvent transferApprovedEvent) {
        kafkaTemplate.send(TOPIC,transferApprovedEvent);
    }
}
