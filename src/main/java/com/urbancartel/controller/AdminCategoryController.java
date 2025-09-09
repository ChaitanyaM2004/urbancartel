package com.urbancartel.controller;

import com.urbancartel.entity.Category;
import com.urbancartel.repository.CategoryRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryRepository categoryRepo;

    @GetMapping
    public List<Category> list() {
        return categoryRepo.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@RequestBody CreateCategory category) {
        Category c = new Category();
        c.setName(category.name);
        c.setDescription(category.description);
        return categoryRepo.save(c);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody CreateCategory category) {
        Category c = categoryRepo.findById(id).orElseThrow(()->new IllegalArgumentException("Category not found"));
        c.setName(category.name);
        c.setDescription(category.description);
        return categoryRepo.save(c);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (categoryRepo.findById(id).isEmpty()) throw new IllegalArgumentException("Category not found");
        categoryRepo.deleteById(id);
    }

    @Data
    static class CreateCategory {
        @NotBlank
        String name;
        String description;
    }
}


