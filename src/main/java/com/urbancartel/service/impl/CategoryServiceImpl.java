package com.urbancartel.service.impl;

import com.urbancartel.dto.CategoryRequest;
import com.urbancartel.dto.CategoryResponse;
import com.urbancartel.entity.Admin;
import com.urbancartel.entity.Roles;
import com.urbancartel.entity.Category;
import com.urbancartel.repository.AdminRepository;
import com.urbancartel.repository.CategoryRepository;
import com.urbancartel.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
private final CategoryRepository categoryRepository;
private final AdminRepository adminRepository;
  public Admin validateAdmin(Long adminId) {
      return adminRepository.findById(adminId).orElseThrow(() -> new RuntimeException("Admin not found"));
  }
    @Override
    public CategoryResponse createForAdmin(Long adminId, CategoryRequest req) {
      System.out.println("hey");
        Admin admin = validateAdmin(adminId);
        if(admin.getRole()== Roles.CATEGORY_ADMIN || admin.getRole()== Roles.SUPER_ADMIN){
            Category category = Category.builder()
                    .name(req.name())
                    .description(req.description())
                    .build();
            categoryRepository.save(category);
            return new CategoryResponse(category.getId(), category.getName(), category.getDescription());
        }
        throw new RuntimeException("unauthorized");
    }

    @Override

    public CategoryResponse updateForAdmin(Long adminId, Long CategoryId, CategoryRequest req) {

        System.out.println("reached at update");

        Admin admin = validateAdmin(adminId);
        String desc = req.description();

        Category category= categoryRepository.findById(CategoryId).orElseThrow(()->new RuntimeException("Category not found"));
        Optional<Category> checkName = categoryRepository.findByNameAndIdNot(req.name(), CategoryId);
        if(checkName.isPresent()){
            System.out.print("name is present");
            if(desc==checkName.get().getDescription()){
                throw new RuntimeException("u are adding a duplicate entry");
            }
            else{
                System.out.println("at desc when name is same");
                category.setDescription(desc);
            }
        }
        else {
            System.out.println("at no common name");
            category.setName(req.name());

            category.setDescription(req.description());
        }
        categoryRepository.save(category);

        return new CategoryResponse(category.getId(), category.getName(), category.getDescription());

    }

    @Override
    public void deleteForAdmin(Long adminId, Long CategoryId) {
      validateAdmin(adminId);
      categoryRepository.deleteById(CategoryId);
    }

    @Override
    public List<CategoryResponse> getAllForAdmin(Long adminId) {
        System.out.println("hey");
        validateAdmin(adminId);
        return categoryRepository.findAll().stream().map(cat->new CategoryResponse(cat.getId(),cat.getName(),cat.getDescription())).toList();
    }
}
