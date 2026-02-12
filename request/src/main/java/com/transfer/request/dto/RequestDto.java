package com.transfer.request.dto;

import com.transfer.request.enums.TransferStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RequestDto {
    private UUID parcelId;
    private UUID fromOwnerId;
    private UUID toOwnerId;
    private TransferStatus status;
    private String reason;
    private UUID createdBy;
}
