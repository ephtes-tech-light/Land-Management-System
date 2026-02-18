package com.land.events;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TransferApprovedEvent {
    private UUID transferId;
    private UUID parcelId;
    private UUID sellerId;
    private UUID buyerId;
    private LocalDateTime approvedAt;
}
