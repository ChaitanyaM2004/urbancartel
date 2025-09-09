package com.urbancartel.repository;

import com.urbancartel.entity.Category;
import com.urbancartel.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
// List<Product> findByCategoryId(Long categoryId);
//// boolean existsByIdAndCategoryId(Long id , Long categoryId);
}
