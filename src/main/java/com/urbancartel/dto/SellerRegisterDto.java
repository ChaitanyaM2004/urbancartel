package com.urbancartel.dto;

import lombok.Data;

@Data
public class SellerRegisterDto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phoneNumber;
    private String shopName;
    private String sellerName;
    private String gstNumber;
    private String bankAccount;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;

}
