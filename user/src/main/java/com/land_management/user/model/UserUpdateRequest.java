package com.land_management.user.model;

import com.land_management.user.status.UserStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class UserUpdateRequest {
    @Id
    private UUID id;
    private UUID userId;
    private String firstName;

    @Enumerated(EnumType.STRING)
    private UserStatus status= UserStatus.PENDING;

    private String middleName;

    private String lastName;

    private String nationalId;

    private String phoneNumber;

    private LocalDateTime updatedAt;

    private String address;
}
