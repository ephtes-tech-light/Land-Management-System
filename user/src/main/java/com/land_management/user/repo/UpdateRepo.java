package com.land_management.user.repo;

import com.land_management.user.model.UserUpdateRequest;
import com.land_management.user.status.UpdateRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UpdateRepo extends JpaRepository<UpdateRepo,Long> {
    List<UserUpdateRequest> findByStatus(UpdateRequestStatus status);
}
