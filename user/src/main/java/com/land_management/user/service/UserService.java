package com.land_management.user.service;


import com.land_management.user.dto.RegistrationDto;
import com.land_management.user.dto.UpdateUserDto;
import com.land_management.user.exception.DuplicateResourceException;
import com.land_management.user.exception.NotFoundException;
import com.land_management.user.mapper.UserMapper;
import com.land_management.user.model.User;
import com.land_management.user.model.UserUpdateRequest;
import com.land_management.user.repo.UpdateRepo;
import com.land_management.user.repo.UserRepo;
import com.land_management.user.status.UserStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepository;
    private final UpdateRepo updateRepo;

    @Autowired
    private KeycloakService keycloakService;

    @Transactional
    public void register(RegistrationDto dto) {
        validateUniqueness(dto);
        log.debug("Validation uniqueness ");
        String keycloakId= keycloakService.createKeycloakUser(dto);
        log.debug("keycloak registration successful ");

        try {
            User user=UserMapper.toEntity(dto);
            user.setKeycloakId(keycloakId);
            userRepository.save(user);
        }
        catch (Exception e) {
            keycloakService.rollbackUser(keycloakId);
            throw new RuntimeException("Registration failed: Database synchronization error.");
        }

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
    public boolean isApprovedUser(UUID id){
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User not approved"));
        return user.getStatus()== UserStatus.APPROVED&&!user.isDeleted();

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
        log.info("saved to update repo");
        return updateRepo.save(ur);
    }
}
