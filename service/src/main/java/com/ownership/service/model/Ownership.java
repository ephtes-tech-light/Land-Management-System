package com.ownership.service.model;

import com.ownership.service.enums.OwnershipStatus;
import com.ownership.service.enums.TenureType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "ownership")
public class Ownership {
    @Id
    @GeneratedValue
    private UUID ownershipId;

    @Column(nullable = false)
    private UUID parcelId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TenureType tenureType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OwnershipStatus status;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    private String legalBasis;
    // marriage certificate, court decision, inheritance letter

    @OneToMany(mappedBy = "ownership", cascade = CascadeType.ALL)
    private List<OwnershipHolder> holders = new ArrayList<>();
}
