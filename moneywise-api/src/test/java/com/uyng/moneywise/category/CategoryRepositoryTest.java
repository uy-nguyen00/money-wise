package com.uyng.moneywise.category;

import com.uyng.moneywise.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CategoryRepository categoryRepository;
    private User user;
    private Category category;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .email("test@mail.com")
                .build();
        entityManager.persist(user);

        category = categoryRepository.save(
                Category.builder()
                        .user(user)
                        .name("Groceries")
                        .type(CategoryType.EXPENSE)
                        .isDefault(true)
                        .build()
        );
    }

    @Test
    public void testCreateCategory() {

        assertThat(category).isNotNull();
        assertThat(category.getId()).isNotNull();
        assertThat(category.getName()).isEqualTo("Groceries");
        assertThat(category.getType()).isEqualTo(CategoryType.EXPENSE);
        assertThat(category.isDefault()).isTrue();
    }

    @Test
    public void testUpdateCategory() {
        category.setName("Utilities");
        category.setType(CategoryType.INCOME);
        category.setDefault(false);

        Category updatedCategory = categoryRepository.save(category);

        assertThat(updatedCategory.getName()).isEqualTo("Utilities");
        assertThat(updatedCategory.getType()).isEqualTo(CategoryType.INCOME);
        assertThat(updatedCategory.isDefault()).isFalse();
    }

    @Test
    public void testRetrieveCategory() {
        Category foundCategory = categoryRepository.findById(category.getId()).orElse(null);

        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getId()).isEqualTo(category.getId());
        assertThat(foundCategory.getName()).isEqualTo("Groceries");
        assertThat(foundCategory.getType()).isEqualTo(CategoryType.EXPENSE);
        assertThat(foundCategory.isDefault()).isTrue();
    }

    @Test
    public void testDeleteCategory() {
        categoryRepository.delete(category);
        Category foundCategory = categoryRepository.findById(category.getId()).orElse(null);
        assertThat(foundCategory).isNull();
    }

    @Test
    public void testCategoryAttributes() {
        assertThat(category.getUser()).isEqualTo(user);
        assertThat(category.getName()).isEqualTo("Groceries");
        assertThat(category.getType()).isEqualTo(CategoryType.EXPENSE);
        assertThat(category.isDefault()).isTrue();
    }
}