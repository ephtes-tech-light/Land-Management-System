package com.transfer.request.repo;

import com.transfer.request.model.TransferApproval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransferAppRepo extends JpaRepository<TransferApproval, UUID> {
}
