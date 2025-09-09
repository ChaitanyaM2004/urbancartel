package com.urbancartel.service.impl;

import com.urbancartel.entity.RefreshToken;
import com.urbancartel.entity.User;
import com.urbancartel.repository.RefreshTokenRepository;
import com.urbancartel.service.RefreshTokenService;
import jakarta.transaction.Transactional;
import org.hibernate.validator.constraints.ScriptAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Value("${app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    @Transactional
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        // Delete existing refresh token for the user
        refreshTokenRepository.deleteByUser(user);

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    ;
    @Override
    @Transactional
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    public void deleteByUserId(User user) {
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUser(user);
        if (existingToken.isPresent()) {
            refreshTokenRepository.deleteByUser(user);
        }
    }
}
