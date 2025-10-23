package com.urbancartel.service;

import com.urbancartel.dto.CategoryRequest;
import com.urbancartel.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
     CategoryResponse createForAdmin(Long adminId , CategoryRequest req);
     CategoryResponse updateForAdmin(Long adminId , Long CategoryId , CategoryRequest req);
     void deleteForAdmin(Long adminId, Long CategoryId);
     List<CategoryResponse> getAllForAdmin(Long adminId);
}
