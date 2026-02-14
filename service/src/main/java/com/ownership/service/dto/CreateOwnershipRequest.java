package com.ownership.service.dto;

import com.ownership.service.enums.OwnershipStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateOwnershipRequest {
    private UUID parcelId;
    private UUID userId;
    private String legalBasis;
}
