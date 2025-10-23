package com.urbancartel.repository;

import com.urbancartel.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long> {

    boolean existsByEmail(String email);

    Optional<Object> findByEmail(String email);
}

