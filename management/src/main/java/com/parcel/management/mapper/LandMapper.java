package com.parcel.management.mapper;

import com.parcel.management.convertor.GeoJsonConverter;
import com.parcel.management.dto.LandResponseDto;
import com.parcel.management.model.Land;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class LandMapper {
    private final GeoJsonConverter geoJsonConverter;

    public LandResponseDto toDto(Land land) {

        if (land == null) {
            return null;
        }
        LandResponseDto dto=new LandResponseDto();
        dto.setLandId(land.getLandId());
        dto.setRegion(land.getRegion());
        dto.setKebele(land.getKebele());
        dto.setZone(land.getZone());
        dto.setWoreda(land.getWoreda());
        dto.setLandType(land.getLandType());
        dto.setDescription(land.getDescription());

        // Geometry → GeoJSON
        dto.setCoordinates(
                geoJsonConverter.toJsonNode(land.getCoordinates())
        );
        return dto;
    }
}