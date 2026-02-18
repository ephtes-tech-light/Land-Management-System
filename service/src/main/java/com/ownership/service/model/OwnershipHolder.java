package com.ownership.service.model;

import com.ownership.service.enums.HolderRole;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "ownership_holder")
@Data
public class OwnershipHolder {

    @Id
    private UUID holderId;

    @Column(nullable = false)
    private UUID userId; // from User Service

    @Enumerated(EnumType.STRING)
    private HolderRole role; // OWNER, SPOUSE, HEIR

    @ManyToOne
    @JoinColumn(name = "ownership_id")
    private Ownership ownership;

    private Double sharePercentage; // nullable for marriage
}
