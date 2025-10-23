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

    @GetMapping()
    public List<Category> list() {
        return categoryRepo.findAll();
    }

    @PostMapping("/addcategory")
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@RequestBody CreateCategory category) {
        Category c = new Category();
        c.setName(category.name);
        c.setDescription(category.description);
        return categoryRepo.save(c);
    }

    @PutMapping("updatecategory/{catid}")
    public Category update(@PathVariable Long catid, @RequestBody CreateCategory category) {
        Category c = categoryRepo.findById(catid).orElseThrow(()->new IllegalArgumentException("Category not found"));
        c.setName(category.name);
        c.setDescription(category.description);
        return categoryRepo.save(c);
    }

    @DeleteMapping("deletecategory/{delid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long delid) {
        if (categoryRepo.findById(delid).isEmpty()) throw new IllegalArgumentException("Category not found");
        categoryRepo.deleteById(delid);
    }

    @Data
    static class CreateCategory {
        @NotBlank
        String name;
        String description;
    }
}


