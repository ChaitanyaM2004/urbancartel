package com.urbancartel.service;

import com.urbancartel.dto.*;
import com.urbancartel.entity.RefreshToken;
import com.urbancartel.entity.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
     public ResponseEntity<?> register(SellerRegisterDto seller);
     public ResponseEntity<?> login(AuthRequest request);
//     public ResponseEntity<?> refresh(TokenRefreshRequest request);
     public ResponseEntity<?> logout(LogoutRequest request);
     ResponseEntity<?> refresh(TokenRefreshRequest request);
//     ResponseEntity<?> createAdmin(AdminCreateRequest request);
}
