package com.urbancartel.service.impl;

import com.urbancartel.dto.ProductRequest;
import com.urbancartel.dto.ProductResponse;
import com.urbancartel.entity.Category;
import com.urbancartel.entity.Product;
import com.urbancartel.entity.Seller;
import com.urbancartel.repository.CategoryRepository;
import com.urbancartel.repository.ProductRepository;
import com.urbancartel.repository.SellerRepository;
import com.urbancartel.service.ProductService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service → Marks this class as a Spring Service (a business logic layer bean). Spring will detect it and register it in the application context.
//
//@RequiredArgsConstructor → From Lombok. It generates a constructor with all final fields (so Spring can inject productRepo, sellerRepo, categoryRepo automatically).
//
//@Transactional → Makes all methods in this class transactional (DB operations either fully succeed or fully roll back if there’s an error).
//
//        implements ProductService → This class is the implementation of the ProductService interface.

@Service
@RequiredArgsConstructor @Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;
    private final SellerRepository sellerRepo;
    private final CategoryRepository categoryRepo;
    @Override
    public ProductResponse createForSeller(Long sellerId, ProductRequest req) {
        Seller seller = sellerRepo.findById(sellerId).orElseThrow(()->new IllegalArgumentException("Invalid seller ID"));
        Category category = categoryRepo.findById(req.categoryId()).orElseThrow(()-> new IllegalArgumentException("category not found"));
        Product product = Product.builder().name(req.name()).description(req.description()).price(req.price()).stock(req.stock()).seller(seller).category(category).imageUrl(req.imageUrl()).build();
        return toDto(productRepo.save(product));
    }

    @Override
    public ProductResponse updateForSeller(Long sellerId, Long ProductId, ProductRequest req) {
        Product product = productRepo.findById(ProductId).orElseThrow(()->new IllegalArgumentException("Invalid product ID"));
        if(!product.getSeller().getId().equals(sellerId)) throw new SecurityException("you can only update your own products");
        Category category = categoryRepo.findById(req.categoryId()).orElseThrow(()->new IllegalArgumentException("category not found"));
        product.setName(req.name());
        product.setDescription(req.description());
        product.setPrice(req.price());
        product.setStock(req.stock());
        product.setCategory(category);
        product.setImageUrl(req.imageUrl());
        return toDto(productRepo.save(product));
    }

    @Override
    public void deleteForSeller(Long sellerId, Long ProductId) {
         if(!productRepo.existsByIdAndSellerId(ProductId, sellerId))  throw new SecurityException("you can only delete your own products");
         productRepo.deleteById(ProductId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllForSeller(Long sellerId) {
        List<Product> products = productRepo.findBySellerId(sellerId);
        return products.stream().map(this::toDto).toList();
    }
// now for admin
    @Override
    public ProductResponse adminCreate(Long sellerId, ProductRequest req) {
        return createForSeller(sellerId , req);
    }

    @Override
    public ProductResponse adminUpdate(Long productId, ProductRequest req) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Category category = categoryRepo.findById(req.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        product.setName(req.name());
        product.setDescription(req.description());
        product.setPrice(req.price());
        product.setStock(req.stock());
        product.setCategory(category);

        return toDto(product);
    }

    @Override
    public void adminDelete(Long productId) {
        if (!productRepo.existsById(productId)) {
            throw new IllegalArgumentException("Product not found");
        }
        productRepo.deleteById(productId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> listAll() {
        return productRepo.findAll().stream().map(this::toDto).toList();
    }



    @Override
    public ProductResponse getById(Long productId) {
        return productRepo.findById(productId).map(this::toDto).orElseThrow(()->new IllegalArgumentException("Product not found"));
    }

    private ProductResponse toDto(Product p){
        return new ProductResponse(
                (long)p.getId(),
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                p.getStock(),
                p.getCategory() != null ? p.getCategory().getId() : null,
                p.getCategory() != null ? p.getCategory().getName() : null,
                (long)p.getSeller().getId(),
                p.getSeller().getShopName(),
                p.getImageUrl()
        );
    }
}
