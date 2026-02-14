package com.transfer.request.repo;

import com.transfer.request.model.TransferApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransferAppRepo extends JpaRepository<TransferApproval, UUID> {
}
