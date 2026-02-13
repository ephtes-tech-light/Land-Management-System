package com.land_management.user.model;

import com.land_management.user.status.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "AppUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID userId;

    @Column(unique = true, nullable = false)
    private String keycloakId;

    @Column(unique = true , nullable = false)
    private String username;


    @Column(unique = true, nullable = false )
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String middleName;

    @Column(nullable = false)
    private String lastName;


    @Column(unique = true, nullable = false )
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private boolean deleted;

    @Builder.Default
    @Column(nullable = false)
    private String role="USER";

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.PENDING;


    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime createdAt=LocalDateTime.now();

    @Column(unique = true, nullable = false, length = 12)
    private String nationalId;
    private LocalDateTime approvedAt;

}
