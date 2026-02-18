package com.transfer.request.repo;

import com.transfer.request.enums.TransferStatus;
import com.transfer.request.model.TransferRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransferReqRepo extends JpaRepository<TransferRequest, UUID> {

    List<TransferRequest> findByStatus(TransferStatus transferStatus);

}
