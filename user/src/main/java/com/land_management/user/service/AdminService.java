package com.land_management.user.service;

import com.land_management.user.dto.UserResponseDto;
import com.land_management.user.exception.NotFoundException;
import com.land_management.user.mapper.UserMapper;
import com.land_management.user.model.User;
import com.land_management.user.model.UserUpdateRequest;
import com.land_management.user.repo.UpdateRepo;
import com.land_management.user.repo.UserRepo;
import com.land_management.user.status.UpdateRequestStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UpdateRepo updateRepo;
    private final UserRepo userRepo;

    @Transactional
    public String rejectUpdateRequest(Long requestId) {
        UserUpdateRequest request = updateRepo.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Update request not found"));

        if (request.getStatus()== UpdateRequestStatus.APPROVED ||
                request.getStatus()==UpdateRequestStatus.REJECTED) {
            throw new IllegalStateException("Update request already processed");
        }

        request.setStatus(UpdateRequestStatus.REJECTED);
        request.setUpdatedAt(LocalDateTime.now());
        updateRepo.save(request);
        return "Rejected Successfully";
    }

    public List<UserUpdateRequest> getPending(){
        return updateRepo.findByStatus(UpdateRequestStatus.PENDING);
    }

    @Transactional
    public UserResponseDto approveUserUpdate(Long requestId){
        UserUpdateRequest userUpdateRequest=updateRepo.findById(requestId).orElseThrow(
                ()->new NotFoundException("User Not Found"));
        if (userUpdateRequest.getStatus()== UpdateRequestStatus.APPROVED ||
                userUpdateRequest.getStatus()==UpdateRequestStatus.REJECTED){
            throw new IllegalStateException("Update request already processed");
        }
        User user=userRepo.findById(requestId).orElseThrow(()->
                new NotFoundException("User not found"));
        user.setAddress(userUpdateRequest.getAddress());
        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setMiddleName(userUpdateRequest.getMiddleName());
        user.setPhoneNumber(userUpdateRequest.getPhoneNumber());
        user.setNationalId(userUpdateRequest.getNationalId());
        userRepo.save(user);

        userUpdateRequest.setStatus(UpdateRequestStatus.APPROVED);
        userUpdateRequest.setUpdatedAt(LocalDateTime.now());
        updateRepo.save(userUpdateRequest);
        return UserMapper.toResponse(user);

    }
    public String approveUser(Long id){
        User user=userRepo.findById(id).orElseThrow(()->new NotFoundException("User not found"));
        user.setStatus(UpdateRequestStatus.APPROVED);
        userRepo.save(user);
        return "Approved Successfully";
    }

    public UserResponseDto getUserById(Long id){
        User user=userRepo.findById(id)
                .orElseThrow(()->new NotFoundException("User Not Found"));
        return UserMapper.toResponse(user);
    }

    public List<UserResponseDto> getAllUser(){
        return userRepo.findAll().stream().map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

}
