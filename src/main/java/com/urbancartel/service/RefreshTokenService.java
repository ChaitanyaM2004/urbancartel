package com.urbancartel.service;

import com.urbancartel.entity.RefreshToken;
import com.urbancartel.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public interface RefreshTokenService {
    @Transactional
    public Optional<RefreshToken> findByToken(String token);
    @Transactional
    RefreshToken createRefreshToken(User user);
    @Transactional
    RefreshToken verifyExpiration(RefreshToken token);
    @Transactional
    public void deleteByUserId(User user);

}
