package com.transfer.request.service;

import com.transfer.request.repo.TransferAppRepo;
import com.transfer.request.repo.TransferReqRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final TransferAppRepo transferAppRepo;
    private final TransferReqRepo transferReqRepo;

    public void createTransfer()
}
