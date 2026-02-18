package com.ownership.service.event;

import com.land.events.TransferApprovedEvent;
import com.ownership.service.service.OwnershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferEventListener {
    OwnershipService ownershipService;
    @KafkaListener(topics = "transfer-approved",groupId = "ownership-group")
    public void handleTransferApproved(TransferApprovedEvent transferApprovedEvent) {
        ownershipService.updateIndividualOwnership(transferApprovedEvent);
    }
}
