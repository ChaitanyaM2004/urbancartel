package com.urbancartel.service;

import com.urbancartel.dto.ProductRequest;
import com.urbancartel.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    //for seller
 ProductResponse createForSeller(Long sellerId , ProductRequest req);
 ProductResponse updateForSeller(Long sellerId , Long ProductId , ProductRequest req);
 void deleteForSeller(Long sellerId, Long ProductId);
 List<ProductResponse> getAllForSeller(Long sellerId);

  //for admin
  ProductResponse adminCreate(Long sellerId,ProductRequest req);
  ProductResponse adminUpdate(Long productId , ProductRequest req);
  void adminDelete(Long productId);
  List<ProductResponse> listAll();
  ProductResponse getById(Long id);

}
