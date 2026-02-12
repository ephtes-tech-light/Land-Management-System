package com.transfer.request.service;

import com.transfer.request.dto.RequestDto;
import com.transfer.request.enums.ApprovalRole;
import com.transfer.request.enums.ApprovalStatus;
import com.transfer.request.enums.TransferStatus;
import com.transfer.request.model.TransferApproval;
import com.transfer.request.model.TransferRequest;
import com.transfer.request.repo.TransferAppRepo;
import com.transfer.request.repo.TransferReqRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final TransferAppRepo transferAppRepo;
    private final TransferReqRepo transferReqRepo;

    public TransferRequest createTransfer(RequestDto dto) {
        TransferRequest tr = new TransferRequest();
        tr.setParcelId(dto.getParcelId());
        tr.setFromOwnerId(dto.getFromOwnerId());
        tr.setToOwnerId(dto.getToOwnerId());
        tr.setCreatedBy(dto.getCreatedBy());
        tr.setCreatedAt(LocalDateTime.now());
        tr.setStatus(dto.getStatus());
        tr.setReason(dto.getReason());

        return transferReqRepo.save(tr);
    }

    public TransferRequest approve(UUID approverId, UUID transferId, ApprovalRole approvalRole, boolean approved, String comment) {


        TransferRequest tRequest = transferReqRepo.findById(approverId).orElse(null);

        if (tRequest.getStatus() == TransferStatus.APPROVED ||
                tRequest.getStatus() == TransferStatus.REJECTED) {
            throw new IllegalStateException("Transfer already finalized");
        }
        TransferApproval approval = new TransferApproval();
        approval.setTransferId(transferId);
        approval.setApproverId(approverId);
        approval.setRole(approvalRole);
        approval.setStatus(approved ? ApprovalStatus.APPROVED : ApprovalStatus.REJECTED);
        approval.setComment(comment);
        transferAppRepo.save(approval);

        if (!approved) {
            tRequest.setStatus(TransferStatus.REJECTED);
        } else {
            if (approvalRole == ApprovalRole.SUBCITY &&
                    tRequest.getStatus() == TransferStatus.PENDING_SUBCITY) {

                tRequest.setStatus(TransferStatus.PENDING_CITY);

            } else if (approvalRole == ApprovalRole.CITY &&
                    tRequest.getStatus() == TransferStatus.PENDING_CITY) {

                tRequest.setStatus(TransferStatus.APPROVED);

            } else {
                throw new IllegalStateException("Invalid approval state");
            }
            transferReqRepo.save(tRequest);

        }
        return tRequest;
    }
}