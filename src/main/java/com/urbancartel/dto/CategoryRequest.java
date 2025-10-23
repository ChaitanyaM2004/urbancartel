package com.urbancartel.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
public record CategoryRequest(
        @NotBlank @Size(max=150)
         String name,
        @Size(max=500)
        String description
){}
