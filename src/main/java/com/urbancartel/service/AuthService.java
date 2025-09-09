package com.urbancartel.service;

import com.urbancartel.dto.AuthRequest;
import com.urbancartel.dto.LogoutRequest;
import com.urbancartel.dto.SellerRegisterDto;
import com.urbancartel.dto.TokenRefreshRequest;
import com.urbancartel.entity.RefreshToken;
import com.urbancartel.entity.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
     public ResponseEntity<?> register(SellerRegisterDto seller);
     public ResponseEntity<?> login(AuthRequest request);
//     public ResponseEntity<?> refresh(TokenRefreshRequest request);
     public ResponseEntity<?> logout(LogoutRequest request);
     ResponseEntity<?> refresh(TokenRefreshRequest request);
}
