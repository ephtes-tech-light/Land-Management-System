package com.land_management.user.repo;

import com.land_management.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {

     boolean existsByUsername(String username);

     boolean existsByNationalId(String NationalId);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);
}
