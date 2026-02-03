package com.ownership.service.controller;

import com.ownership.service.dto.CreateOwnershipRequest;
import com.ownership.service.model.Ownership;
import com.ownership.service.service.OwnershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ownerships")
@RequiredArgsConstructor
public class OwnershipController {
    private final OwnershipService ownershipService;

    @PostMapping("/individual")
    public ResponseEntity<?> createIndividualOwnership(
            @RequestBody CreateOwnershipRequest request) {

        Ownership ownership = ownershipService
                .createIndividualOwnership(
                        request.getParcelId(),
                        request.getUserId()
                );

        return ResponseEntity.ok(ownership.getOwnershipId());
}}
