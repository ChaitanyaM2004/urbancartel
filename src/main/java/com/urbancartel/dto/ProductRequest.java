package com.urbancartel.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
public record ProductRequest(
        @NotBlank @Size(max = 150) String name,
        @Size(max = 500) String description,
        @NotNull @DecimalMin("0.0") BigDecimal price,
        @NotNull @Min(0) Integer stock,
        @NotNull Long categoryId,
        String imageUrl
) {}



