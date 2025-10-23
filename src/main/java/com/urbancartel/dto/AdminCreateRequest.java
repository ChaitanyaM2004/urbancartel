package com.urbancartel.dto;

import com.urbancartel.entity.Role;
import com.urbancartel.entity.Roles;
import lombok.Data;

import java.util.Set;

@Data
public class AdminCreateRequest {
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String email;
    private String password;
    private String role;

}
