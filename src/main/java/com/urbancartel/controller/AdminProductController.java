package com.urbancartel.controller;

import com.urbancartel.dto.ProductRequest;
import com.urbancartel.dto.ProductResponse;
import com.urbancartel.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {
    private final ProductService productService;

    @PostMapping("/{sellerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createForSeller(@PathVariable Long sellerId, @Valid @RequestBody ProductRequest req) {
        return productService.adminCreate(sellerId, req);
    }

    @PutMapping("/{productId}")
    public ProductResponse update(@PathVariable Long productId, @Valid @RequestBody ProductRequest req) {
        return productService.adminUpdate(productId, req);
    }
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long productId) {
        productService.adminDelete(productId);
    }
    @GetMapping
    public List<ProductResponse> listAll() {
        return productService.listAll();
    }
    @GetMapping("/{productId}")
    public ProductResponse getOne(@PathVariable Long productId) {
        return productService.getById(productId);
    }


}