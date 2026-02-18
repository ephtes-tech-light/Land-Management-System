package com.ownership.service.service;


import com.land.events.TransferApprovedEvent;
import com.ownership.service.dto.CreateOwnershipRequest;
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
    public Ownership createIndividualOwnership(CreateOwnershipRequest cw) {
        if (ownershipRepo.existsByParcelIdAndStatus(cw.getParcelId(), OwnershipStatus.ACTIVE)){
            throw new IllegalStateException("Active ownership already exists");
        }

        Ownership ownership = new Ownership();
        ownership.setParcelId(cw.getParcelId());
        ownership.setTenureType(TenureType.INDIVIDUAL);
        ownership.setStatus(OwnershipStatus.ACTIVE);
        ownership.setStartDate(LocalDate.now());
        ownership.setLegalBasis(cw.getLegalBasis());

        OwnershipHolder holder = new OwnershipHolder();
        holder.setUserId(cw.getUserId());
        holder.setRole(HolderRole.OWNER);
        holder.setSharePercentage(100.0);
        holder.setOwnership(ownership);

        ownership.getHolders().add(holder);

        Ownership saved = ownershipRepo.save(ownership);
        //eventPublisher.publishOwnershipCreated(saved); do it later
        return saved;
        }

        @Transactional
        public void updateIndividualOwnership(TransferApprovedEvent transferEventApprovedEvent) {
            Ownership currentOwnership=ownershipRepo.findById(transferEventApprovedEvent.getSellerId()).orElseThrow(null);
            currentOwnership.setStatus(OwnershipStatus.TRANSFERRED);
            currentOwnership.setEndDate(LocalDate.now());

            Ownership newOwnership=new Ownership();
            newOwnership.setParcelId(transferEventApprovedEvent.getParcelId());
            newOwnership.setTenureType(TenureType.INDIVIDUAL);
            newOwnership.setStatus(OwnershipStatus.ACTIVE);
            newOwnership.setStartDate(LocalDate.now());
            newOwnership.setLegalBasis("transfered: "+transferEventApprovedEvent.getTransferId());

            OwnershipHolder holder = new OwnershipHolder();
            holder.setUserId(transferEventApprovedEvent.getBuyerId());
            holder.setRole(HolderRole.OWNER);
            holder.setSharePercentage(100.0);
            holder.setOwnership(newOwnership);
            newOwnership.getHolders().add(holder);

            ownershipRepo.save(currentOwnership);

        }
    }


