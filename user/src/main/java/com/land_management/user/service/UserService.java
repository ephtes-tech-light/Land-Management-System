package com.land_management.user.service;


import com.land_management.user.dto.RegistrationDto;
import com.land_management.user.repo.UpdateRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UpdateRepo userRepository;
    private final UpdateRepo updateRepo;
    private final KeyCloakService keyCloakService;


    public void register(RegistrationDto dto) {
        validateUniqueness(dto);
        log.info("Validation uniquness ");
        keyCloakService.registerUser(dto.getUsername(),dto.getEmail(),
                dto.getPassword());
        log.info("keycloak regiteration successful ");

        /*User user = UserMapper.toEntity(dto);
        user.setKeycloakId(kcUserId);
        User saved = userRepository.save(user);
        return UserMapper.toResponse(saved);*/
    }

    private void validateUniqueness(RegistrationDto registrationDTO){
        if (userRepository.existsByPhoneNumber(registrationDTO.getPhoneNumber())){
            throw new DuplicateResourceException("Phone number already exist");
        }
        if (userRepository.existsByNationalId(registrationDTO.getNationalId())){
            throw new DuplicateResourceException("National id already exist");
        }
        if (userRepository.existsByUsername(registrationDTO.getUsername())){
            throw new DuplicateResourceException("Username already exist");
        }
        if (userRepository.existsByEmail(registrationDTO.getEmail())){
            throw new DuplicateResourceException("Email already exist");
        }

    }
    public List<UserResponseDto> gellAllUser(){
        return userRepository.findAll().stream().map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserById(Long id){
        User user=userRepository.findById(id)
                .orElseThrow(()->new NotFoundException("User Not Found"));
        return UserMapper.toResponse(user);
    }


    @Transactional
    public UserUpdateRequest updateUserDto(UpdateUserDto dto){
        UserUpdateRequest ur=new UserUpdateRequest();
        log.info("Created user update request");
        if(!userRepository.existsById(dto.getUserId())){
            throw new NotFoundException("User not found");
        }
        ur.setUserId(dto.getUserId());
        ur.setFirstName(dto.getFirstName());
        ur.setMiddleName(dto.getMiddleName());
        ur.setPhoneNumber(dto.getPhoneNumber());
        ur.setNationalId(dto.getNationalId());
        ur.setLastName(dto.getLastName());
        ur.setAddress(dto.getAddress());
        ur.setUpdatedAt(LocalDateTime.now());
        log.info("seted ur from dto");

        log.info("saved to update repo");
        return updateRepo.save(ur);
    }

    @Transactional
    public UserResponseDto approveUserUpdate(Long requestId){
        UserUpdateRequest userUpdateRequest=updateRepo.findById(requestId).orElseThrow(
                ()->new NotFoundException("User Not Found"));
        if (userUpdateRequest.getStatus()== UpdateRequestStatus.APPROVED ||
                userUpdateRequest.getStatus()==UpdateRequestStatus.REJECTED){
            throw new IllegalStateException("Update request already processed");
        }
        User user=userRepository.findById(requestId).orElseThrow(()->
                new NotFoundException("User not found"));
        user.setAddress(userUpdateRequest.getAddress());
        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setMiddleName(userUpdateRequest.getMiddleName());
        user.setPhoneNumber(userUpdateRequest.getPhoneNumber());
        user.setNationalId(userUpdateRequest.getNationalId());
        userRepository.save(user);

        userUpdateRequest.setStatus(UpdateRequestStatus.APPROVED);
        userUpdateRequest.setUpdatedAt(LocalDateTime.now());
        updateRepo.save(userUpdateRequest);
        return UserMapper.toResponse(user);

    }

    public List<UserUpdateRequest> getPending(){
        return updateRepo.findByStatus(UpdateRequestStatus.PENDING);
    }
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

}
