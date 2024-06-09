package com.uyng.moneywise.category;

import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {

    public Category toCategory(CategoryRequest request) {
        return Category.builder()
                .name(request.name())
                .type(request.type())
                .build();
    }
}
