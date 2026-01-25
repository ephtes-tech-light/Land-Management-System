package com.land_management.user.repo;

import com.land_management.user.model.UserUpdateRequest;
import com.land_management.user.status.UpdateRequestStatus;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UpdateRepo extends JpaRepository<UserUpdateRequest,Long> {
    List<UserUpdateRequest> findByStatus(UpdateRequestStatus status);
}
