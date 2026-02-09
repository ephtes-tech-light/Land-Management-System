package com.land_management.user.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UpdateUserDto {
    private UUID userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nationalId;
    private String phoneNumber;
    private String address;
}
