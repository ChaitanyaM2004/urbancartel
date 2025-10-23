package com.urbancartel.service;

import com.urbancartel.dto.SellerRequest;
import com.urbancartel.dto.SellerResponse;

import java.util.List;

public interface AdminSellerService {
    SellerResponse createSellerByAdmin(Long adminId , SellerRequest req);
    SellerResponse updateSellerByAdmin(Long adminId , Long sellerId , SellerRequest req);
    void deleteForAdmin(Long adminId , Long sellerId);
    List<SellerResponse> getAllForAdmin(Long adminId);
    void blockSellerByAdmin(Long adminId , Long sellerId , String reason);
    void unblockSellerByAdmin(Long adminId , Long sellerId);
}
