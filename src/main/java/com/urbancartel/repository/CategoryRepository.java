package com.urbancartel.repository;

import com.urbancartel.entity.Category;
import com.urbancartel.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
 Optional<Category> findByNameAndIdNot(String name, Long id);
 boolean existsByName(String name);
}
