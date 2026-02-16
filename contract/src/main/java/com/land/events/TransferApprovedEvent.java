package com.land.events;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransferApprovedEvent {
    private UUID parcelId;
    private UUID sellerId;
    private UUID buyerId;
    private LocalDateTime approvedAt;
}
