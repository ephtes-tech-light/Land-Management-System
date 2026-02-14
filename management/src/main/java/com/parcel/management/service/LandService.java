package com.parcel.management.service;

import com.parcel.management.dto.LandRequestDto;
import com.parcel.management.dto.LandResponseDto;
import tools.jackson.databind.JsonNode;

import java.util.List;
import java.util.UUID;

public interface LandService {
    List<LandResponseDto> findLandByLocation(double longitude, double latitude);

    LandResponseDto registerLand(LandRequestDto dto);

    LandResponseDto getLand(UUID id);

    List<LandResponseDto> getAllLands();

    LandResponseDto updateLand(UUID id,LandRequestDto landRequestDto);

    void deleteLand(UUID id);
    List<LandResponseDto> findContainingPoint(double lon, double lat);

    List<LandResponseDto> findWithinBoundary(JsonNode boundary);

    List<LandResponseDto> findNearby(JsonNode geometry, double distanceMeters);


}