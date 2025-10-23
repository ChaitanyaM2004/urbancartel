package com.urbancartel.controller;

import com.urbancartel.dto.*;
import com.urbancartel.entity.Admin;
import com.urbancartel.entity.Roles;
import com.urbancartel.repository.AdminRepository;
import com.urbancartel.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthService authService;
    @Autowired
    private AdminRepository adminRepository;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SellerRegisterDto sdto){
        return authService.register(sdto);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest){
        System.out.println("At login api");
        return authService.login(authRequest);
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request){
        return authService.refresh(request);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest request){
        return authService.logout(request);
    }

    @PostMapping("/check")
    public ResponseEntity<String> check() {;
        return ResponseEntity.ok("API is working");
    }

    @PostMapping("/createadmin")
    public ResponseEntity<?> createAdmin(@RequestBody AdminCreateRequest request) {
        if (adminRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Admin already exists");
        }

        Roles role;
        try {
            role = Roles.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException ex) {
            String allowed = Arrays.stream(Roles.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Invalid role. Allowed: " + allowed);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Admin admin = new Admin();
        admin.setFirstname(request.getFirstname());
        admin.setLastname(request.getLastname());
        admin.setPhoneNumber(request.getPhoneNumber());
        admin.setEmail(request.getEmail());
        admin.setPassword(encoder.encode(request.getPassword()));
        admin.setRole(role); // single role
        admin.setIsActive(true);

        adminRepository.save(admin);
        return ResponseEntity.ok("Admin created successfully");
    }


//    @PostMapping("/createadmin")
//    public ResponseEntity<?> createAdmin(@RequestBody AdminCreateRequest request) {
//        System.out.println("At create admin api");
//
//        if (adminRepository.existsByEmail(request.getEmail())) {
//            return ResponseEntity.badRequest().body("Admin already exists");
//        }
//
//        if (request.getRoles() == null || request.getRoles().isEmpty()) {
//            return ResponseEntity.badRequest().body("At least one role is required");
//        }
//
//        Set<Roles> roles;
//        try {
//            roles = request.getRoles().stream()
//                    .map(String::trim)
//                    .filter(s -> !s.isEmpty())
//                    .map(s -> Roles.valueOf(s.toUpperCase()))
//                    .collect(Collectors.toSet());
//        } catch (IllegalArgumentException ex) {
//            String allowed = Arrays.stream(Roles.values())
//                    .map(Enum::name)
//                    .collect(Collectors.joining(", "));
//            return ResponseEntity.badRequest().body("Invalid role provided. Allowed: " + allowed);
//        }
//
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        Admin admin = new Admin();
//        admin.setFirstname(request.getFirstname());
//        admin.setLastname(request.getLastname());
//        admin.setPhoneNumber(request.getPhoneNumber());
//        admin.setEmail(request.getEmail());
//        admin.setPassword(encoder.encode(request.getPassword()));
//        admin.setRoles(roles); // ✅ Only use the inherited set
//
//        adminRepository.save(admin);
//        return ResponseEntity.ok("Admin created successfully");
//    }



}
