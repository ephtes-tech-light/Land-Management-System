package com.parcel.management.dto;


import com.parcel.management.enumirator.LandType;
import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.JsonNode;

@Data
@Builder
public class LandResponseDto {
    private Long landId;
    private String region;
    private String zone;
    private String woreda;
    private String kebele;
    private LandType landType;
    private String description;
    private JsonNode coordinates;
}
