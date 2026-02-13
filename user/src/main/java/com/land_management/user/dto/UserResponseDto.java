package com.land_management.user.dto;

import com.land_management.user.status.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserResponseDto {
    private UUID id;

    private String username;

    private String email;

    private String firstName;

    private String middleName;

    private String lastName;

    private String address;

    private String nationalId;

    private UserStatus status;

    private String role;


    private String phoneNumber;

}
