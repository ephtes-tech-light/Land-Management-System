package com.ownership.service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateOwnershipRequest {
    private UUID parcelId;
    private UUID userId;
}
