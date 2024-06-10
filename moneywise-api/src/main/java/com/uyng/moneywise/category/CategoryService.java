package com.uyng.moneywise.category;

import com.uyng.moneywise.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponse createCategory(CategoryRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Category category = categoryMapper.toCategory(request);
        category.setUser(user);
        category.setDefault(false);

        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(savedCategory);
    }

    public List<CategoryResponse> findAllCategoriesByUser(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        List<Category> categories = categoryRepository.findByUserEmail(user.getEmail());
        return categories.stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    public CategoryResponse updateCategory(Integer id, CategoryRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Category category = categoryRepository.findByIdAndUserEmail(id, user.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Category not found."));

        Category reqCategory = categoryMapper.toCategory(request);

        Category updatedCategory = categoryRepository.save(
                Category.builder()
                        .name(reqCategory.getName())
                        .type(reqCategory.getType())
                        .id(category.getId())
                        .user(category.getUser())
                        .isDefault(category.isDefault())
                        .build()
        );

        return categoryMapper.toCategoryResponse(updatedCategory);
    }
}
