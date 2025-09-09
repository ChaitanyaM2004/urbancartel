package com.urbancartel.controller;

import com.urbancartel.dto.ProductRequest;
import com.urbancartel.dto.ProductResponse;
import com.urbancartel.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/seller/{sellerId}/products")
@RequiredArgsConstructor
public class SellerProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@PathVariable Long sellerId , @Valid @RequestBody ProductRequest req){
        return productService.createForSeller(sellerId,req);
    }
    @PutMapping("/{productId}")
    public ProductResponse update(@PathVariable Long sellerId , @PathVariable Long productId , @Valid @RequestBody ProductRequest req){
            return productService.updateForSeller(sellerId,productId,req);
        }
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long sellerId, @PathVariable Long productId) {
        productService.deleteForSeller(sellerId, productId);
    }
    @GetMapping
    public List<ProductResponse> list(@PathVariable Long sellerId) {
        return productService.getAllForSeller(sellerId);
    }

}
