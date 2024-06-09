package com.uyng.moneywise.category;

import com.uyng.moneywise.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public Category createCategory(CategoryRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Category category = categoryMapper.toCategory(request);
        category.setUser(user);
        category.setDefault(false);
        return categoryRepository.save(category);
    }
}