package com.ownership.service.service;


import com.ownership.service.enums.HolderRole;
import com.ownership.service.enums.OwnershipStatus;
import com.ownership.service.enums.TenureType;
import com.ownership.service.model.Ownership;
import com.ownership.service.model.OwnershipHolder;
import com.ownership.service.repo.OwnershipRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OwnershipService {

        private final OwnershipRepository ownershipRepo;
      //  private final EventPublisher eventPublisher;  do it later

    @Transactional
    public Ownership createIndividualOwnership(
            UUID parcelId, UUID userId) {

        Ownership ownership = new Ownership();
        ownership.setParcelId(parcelId);
        ownership.setTenureType(TenureType.INDIVIDUAL);
        ownership.setStatus(OwnershipStatus.ACTIVE);
        ownership.setStartDate(LocalDate.now());

        OwnershipHolder holder = new OwnershipHolder();
        holder.setUserId(userId);
        holder.setRole(HolderRole.OWNER);
        holder.setSharePercentage(100.0);
        holder.setOwnership(ownership);

        ownership.getHolders().add(holder);

        Ownership saved = ownershipRepo.save(ownership);
        //   eventPublisher.publishOwnershipCreated(saved); do it later

        return saved;
        }
    }


