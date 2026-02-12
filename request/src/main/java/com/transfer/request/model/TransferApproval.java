package com.transfer.request.model;

import com.transfer.request.enums.ApprovalRole;
import com.transfer.request.enums.ApprovalStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class TransferApproval {
    @Id
    private UUID id;

    private UUID transferId;
    private UUID approverId;

    @Enumerated(EnumType.STRING)
    private ApprovalRole role;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    private String comment;
    private LocalDateTime approvedAt;
}
