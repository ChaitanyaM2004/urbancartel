package com.urbancartel.dto;

public record SellerRequest(    
         String firstname,
         String lastname,
         String email,
         String password,
         String phoneNumber,
         String shopName,
         String sellerName,
         String gstNumber,
         String bankAccount,
         String addressLine1,
         String addressLine2,
         String city,
         String state,
         String postalCode,
         Boolean active,
         String country) {
}
