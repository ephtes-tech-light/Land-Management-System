package com.transfer.request.repo;

import com.transfer.request.model.TransferRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransferReqRepo extends JpaRepository<TransferRequest, UUID> {

}
