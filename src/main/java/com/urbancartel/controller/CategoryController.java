package com.urbancartel.controller;

import com.urbancartel.dto.CategoryRequest;
import com.urbancartel.dto.CategoryResponse;
import com.urbancartel.service.CategoryService;
import com.urbancartel.service.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryServiceImpl csi;

    @PostMapping("/{adminId}")
    public ResponseEntity<CategoryResponse> createCategory(@PathVariable Long adminId, @RequestBody CategoryRequest req) {
        return ResponseEntity.ok(categoryService.createForAdmin(adminId, req));
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<List<CategoryResponse>> getAllForAdmin(@PathVariable Long adminId) {
        return ResponseEntity.ok(categoryService.getAllForAdmin(adminId));
    }

    @PutMapping("/{adminId}/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long adminId, @PathVariable Long categoryId, @RequestBody CategoryRequest req) {
        System.out.print("at put");
        return ResponseEntity.ok(categoryService.updateForAdmin(adminId, categoryId, req));
    }


    @DeleteMapping("/{adminId}/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long adminId, @PathVariable Long categoryId) {
        categoryService.deleteForAdmin(adminId, categoryId);
        return ResponseEntity.noContent().build();
    }
}
