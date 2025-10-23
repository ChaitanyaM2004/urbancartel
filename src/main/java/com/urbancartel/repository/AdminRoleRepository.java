package com.urbancartel.repository;

import com.urbancartel.entity.Roles;

import java.util.Optional;

public interface AdminRoleRepository {
    Optional<Roles> findByName(String name);
}
