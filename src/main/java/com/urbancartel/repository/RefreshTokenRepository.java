package com.urbancartel.repository;

import com.urbancartel.entity.RefreshToken;
import com.urbancartel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String token);
    int deleteByUser(User user);
    Optional<RefreshToken> findByUser(User user);
    void deleteByToken(String token);
}
