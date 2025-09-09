package com.urbancartel.controller;

import com.urbancartel.dto.*;
import com.urbancartel.entity.User;
import com.urbancartel.security.JwtUtil;
import com.urbancartel.service.AuthService;
import com.urbancartel.service.impl.AuthServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SellerRegisterDto sdto){
        return authService.register(sdto);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest){
        Logger logger = (Logger) LoggerFactory.getLogger(AuthServiceImpl.class);
        logger.info("bhenchod");
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

}
