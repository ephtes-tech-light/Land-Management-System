package com.land_management.user.repo;

import com.land_management.user.model.UserUpdateRequest;
import com.land_management.user.status.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UpdateRepo extends JpaRepository<UserUpdateRequest, UUID> {
    List<UserUpdateRequest> findByStatus(UserStatus status);
}
