package com.transfer.request.model;

import com.transfer.request.enums.TransferStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class TransferRequest {
    @Id
    private UUID id;

    private UUID parcelId;
    private UUID fromOwnerId;
    private UUID toOwnerId;

    @Enumerated(EnumType.STRING)
    private TransferStatus status;

    private String reason;
    private UUID createdBy;
    private LocalDateTime createdAt;
}
