package com.urbancartel.dto;

public record SellerResponse(
        Long id,
        String shopName,
        String sellerName,
        String email,
        Boolean isActive
) {
}
