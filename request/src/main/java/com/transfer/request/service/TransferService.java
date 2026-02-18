package com.transfer.request.service;

import com.land.events.TransferApprovedEvent;
import com.transfer.request.dto.RequestDto;
import com.transfer.request.enums.ApprovalRole;
import com.transfer.request.enums.ApprovalStatus;
import com.transfer.request.enums.TransferStatus;
import com.transfer.request.event.TransferEventPublisher;
import com.transfer.request.model.TransferApproval;
import com.transfer.request.model.TransferRequest;
import com.transfer.request.repo.TransferAppRepo;
import com.transfer.request.repo.TransferReqRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final TransferAppRepo transferAppRepo;
    private final TransferReqRepo transferReqRepo;
    private final TransferEventPublisher transferEventPublisher;

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

    public List<TransferRequest> listRequestedTransfer(){
        return transferReqRepo.findByStatus(TransferStatus.PENDING_SUBCITY);
    }
    public List<TransferRequest> listPemdingCityTransferRequest(){
        return transferReqRepo.findByStatus(TransferStatus.PENDING_CITY);
    }

    public TransferRequest approve(UUID approverId, UUID transferId, ApprovalRole approvalRole, boolean approved, String comment) {


        TransferRequest tRequest = transferReqRepo.findById(transferId).orElse(null);

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
            transferReqRepo.save(tRequest);
            return tRequest;
        }
        if (approvalRole == ApprovalRole.SUBCITY &&
                    tRequest.getStatus() == TransferStatus.PENDING_SUBCITY) {

                tRequest.setStatus(TransferStatus.PENDING_CITY);
            }
        if (approvalRole == ApprovalRole.CITY &&
                    tRequest.getStatus() == TransferStatus.PENDING_CITY) {

            tRequest.setStatus(TransferStatus.APPROVED);
            TransferApprovedEvent transferApprovedEvent=new TransferApprovedEvent(
                    tRequest.getId(), tRequest.getParcelId(),tRequest.getFromOwnerId(), tRequest.getToOwnerId(),LocalDateTime.now()
            );
            transferEventPublisher.transferApproveEventPublish(transferApprovedEvent);

            } else {
                throw new IllegalStateException("Invalid approval state");
            }
            return transferReqRepo.save(tRequest);
        }

    }
