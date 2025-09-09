package com.urbancartel.dto;

import com.urbancartel.entity.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private UserDto user;
    private String accessToken;
}
