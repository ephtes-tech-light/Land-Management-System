package com.land_management.user.mapper;

import com.land_management.user.dto.RegistrationDto;
import com.land_management.user.dto.UserResponseDto;
import com.land_management.user.model.User;

import java.time.LocalDateTime;

public class UserMapper {
    private UserMapper() {}

    public static User toEntity(RegistrationDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setMiddleName(dto.getMiddleName());
        user.setLastName(dto.getLastName());
        user.setAddress(dto.getAddress());
        user.setNationalId(dto.getNationalId());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setRole("USER");
        user.setStatus("PENDING_VERIFICATION");
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }

    public static UserResponseDto toResponse(User user) {
        return UserResponseDto.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .role(user.getRole())
                .status(user.getStatus())
                .nationalId(user.getNationalId())
                .build();
    }
}
