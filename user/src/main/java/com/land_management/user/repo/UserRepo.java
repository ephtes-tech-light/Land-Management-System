package com.land_management.user.repo;

import com.land_management.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByNationalId(String NationalId);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);
}
