package com.ownership.service.repo;

import com.ownership.service.enums.OwnershipStatus;
import com.ownership.service.model.Ownership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface  OwnershipRepository extends JpaRepository<Ownership, UUID> {
    Optional<Ownership> findByParcelIdAndStatus(
            UUID parcelId, OwnershipStatus status
    );

    boolean existsByParcelIdAndStatus(UUID parcelId, OwnershipStatus status);
}
