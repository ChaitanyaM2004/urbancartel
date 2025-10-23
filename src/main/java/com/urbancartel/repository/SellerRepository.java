package com.urbancartel.repository;

import com.urbancartel.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {
        Optional<Seller> findSellerById(Long id);
        Optional<Seller> findByEmail(String email);
        Optional<Seller> findByPhoneNumber(String phoneNumber);
        Optional<Seller> findByGstNumber(String gstNumber);
        Optional<Seller> findByShopName(String shopName);
}

