package com.parcel.management.service;

import com.parcel.management.dto.LandRequestDto;
import com.parcel.management.dto.LandResponseDto;
import tools.jackson.databind.JsonNode;

import java.util.List;

public interface LandService {
    List<LandResponseDto> findLandByLocation(double longitude, double latitude);

    LandResponseDto registerLand(LandRequestDto dto);

    LandResponseDto getLand(Long id);

    List<LandResponseDto> getAllLands();

    LandResponseDto updateLand(Long id,LandRequestDto landRequestDto);

    void deleteLand(Long id);
    List<LandResponseDto> findContainingPoint(double lon, double lat);

    List<LandResponseDto> findWithinBoundary(JsonNode boundary);

    List<LandResponseDto> findNearby(JsonNode geometry, double distanceMeters);


}