package com.land_management.user.dto;

import com.land_management.user.status.UpdateRequestStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponseDto {
    private Long id;

    private String username;

    private String email;

    private String firstName;

    private String middleName;

    private String lastName;

    private String address;

    private String nationalId;

    private UpdateRequestStatus status;

    private String role;


    private String phoneNumber;

}
