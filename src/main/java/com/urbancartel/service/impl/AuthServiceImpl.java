package com.urbancartel.service.impl;

import com.urbancartel.dto.*;
import com.urbancartel.entity.Admin;
import com.urbancartel.entity.RefreshToken;
import com.urbancartel.entity.Seller;
import com.urbancartel.entity.User;
import com.urbancartel.repository.AdminRepository;
import com.urbancartel.repository.RefreshTokenRepository;
import com.urbancartel.repository.SellerRepository;
import com.urbancartel.repository.UserRepository;
import com.urbancartel.security.JwtUtil;
import com.urbancartel.service.AuthService;
import com.urbancartel.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired private AuthenticationManager authManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private AdminRepository adminRepository;


    @Override
    public ResponseEntity<?> register(@RequestBody SellerRegisterDto dto) {
        if(sellerRepository.findByEmail(dto.getEmail()).isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: Email is already in use!");
        }
        if(sellerRepository.findByPhoneNumber(dto.getPhoneNumber()).isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body("Error: Phone number is already in use!");
        }
        if(sellerRepository.findByGstNumber(dto.getGstNumber()).isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body("Error: GST number is already in use!");
        }

        Seller seller = Seller.builder()
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .phoneNumber(dto.getPhoneNumber())
                .shopName(dto.getShopName())
                .sellerName(dto.getSellerName())
                .gstNumber(dto.getGstNumber())
                .bankAccount(dto.getBankAccount())
                .addressLine1(dto.getAddressLine1())
                .addressLine2(dto.getAddressLine2())
                .city(dto.getCity())
                .state(dto.getState())
                .postalCode(dto.getPostalCode())
                .country(dto.getCountry())
                .build();

        userRepository.save(seller);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Seller registered successfully");
        return ResponseEntity.ok(response);
    }


    @Override
    public ResponseEntity<?> login(AuthRequest request) {
        System.out.println("login");
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            if (authentication.isAuthenticated()) {
                User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()->new RuntimeException("User not found"));
                String accessToken = jwtUtil.generateToken(user);
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

                Map<String, Object> response = new HashMap<>();
                response.put("accessToken", accessToken);
                response.put("refreshToken", refreshToken.getToken());
                response.put("user",new UserDto(user.getId(),user.getEmail()));
                response.put("tokenType", "Bearer");
                return ResponseEntity.ok(response);
            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Login failed: " + e.getMessage());
        }
    }


    public ResponseEntity<?> refresh(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenRepository.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String newAccessToken = jwtUtil.generateToken(user);
                    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);

                    TokenRefreshResponse response = new TokenRefreshResponse(
                            newAccessToken,
                            newRefreshToken.getToken()
                    );
                    return ResponseEntity.ok(response);
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }

//    @Override
//    public ResponseEntity<?> createAdmin(AdminCreateRequest request) {
//        System.out.println("AT CA");
//        if (adminRepository.existsByEmail(request.getEmail())) {
//            return ResponseEntity.badRequest().body("Admin already exists");
//        }
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        Admin admin = new Admin();
//        admin.setFirstname(request.getFirstname());
//        admin.setLastname(request.getLastname());
//        admin.setPhoneNumber(request.getPhoneNumber());
//        admin.setEmail(request.getEmail());
//        admin.setPassword(encoder.encode(request.getPassword()));
//        admin.setRoles(request.getRoles());
//        System.out.println(request.getRoles());
//        adminRepository.save(admin);
//        return ResponseEntity.ok("Admin created successfully");
//    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest request) {
        try {
            if (request.getRefreshToken() != null) {
                refreshTokenService.deleteByUserId(request.User);
            }
            Map<String, String> response = new HashMap<>();
            response.put("message", "Logged out successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Logout failed: " + e.getMessage());
        }
    }

}
