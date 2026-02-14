package com.parcel.management.dto;


import com.parcel.management.enumirator.LandType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.JsonNode;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LandResponseDto {
    private UUID landId;
    private String region;
    private String zone;
    private String woreda;
    private String kebele;
    private LandType landType;
    private String description;
    private JsonNode coordinates;
}
