package com.uyng.moneywise.category;

import com.uyng.moneywise.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateCategory() {
        User user = User.builder()
                .email("test@mail.com")
                .build();
        entityManager.persist(user);

        Category category = Category.builder()
                .user(user)
                .name("Groceries")
                .type(CategoryType.EXPENSE)
                .isDefault(true)
                .build();

        Category savedCategory = categoryRepository.save(category);

        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getCategoryId()).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo("Groceries");
        assertThat(savedCategory.getType()).isEqualTo(CategoryType.EXPENSE);
        assertThat(savedCategory.isDefault()).isTrue();
    }

    @Test
    public void testUpdateCategory() {
        User user = User.builder()
                .email("test@mail.com")
                .build();
        entityManager.persist(user);

        Category category = Category.builder()
                .user(user)
                .name("Groceries")
                .type(CategoryType.EXPENSE)
                .isDefault(true)
                .build();

        Category savedCategory = categoryRepository.save(category);

        savedCategory.setName("Utilities");
        savedCategory.setType(CategoryType.INCOME);
        savedCategory.setDefault(false);

        Category updatedCategory = categoryRepository.save(savedCategory);

        assertThat(updatedCategory.getName()).isEqualTo("Utilities");
        assertThat(updatedCategory.getType()).isEqualTo(CategoryType.INCOME);
        assertThat(updatedCategory.isDefault()).isFalse();
    }

    @Test
    public void testRetrieveCategory() {
        User user = User.builder()
                .email("test@mail.com")
                .build();
        entityManager.persist(user);

        Category category = Category.builder()
                .user(user)
                .name("Groceries")
                .type(CategoryType.EXPENSE)
                .isDefault(true)
                .build();

        Category savedCategory = categoryRepository.save(category);

        Category foundCategory = categoryRepository.findById(savedCategory.getCategoryId()).orElse(null);

        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getCategoryId()).isEqualTo(savedCategory.getCategoryId());
        assertThat(foundCategory.getName()).isEqualTo("Groceries");
        assertThat(foundCategory.getType()).isEqualTo(CategoryType.EXPENSE);
        assertThat(foundCategory.isDefault()).isTrue();
    }

    @Test
    public void testDeleteCategory() {
        User user = User.builder()
                .email("test@mail.com")
                .build();
        entityManager.persist(user);

        Category category = Category.builder()
                .user(user)
                .name("Groceries")
                .type(CategoryType.EXPENSE)
                .isDefault(true)
                .build();

        Category savedCategory = categoryRepository.save(category);

        categoryRepository.delete(savedCategory);

        Category foundCategory = categoryRepository.findById(savedCategory.getCategoryId()).orElse(null);

        assertThat(foundCategory).isNull();
    }

    @Test
    public void testCategoryAttributes() {
        User user = User.builder()
                .email("test@mail.com")
                .build();
        entityManager.persist(user);

        Category category = Category.builder()
                .user(user)
                .name("Groceries")
                .type(CategoryType.EXPENSE)
                .isDefault(true)
                .build();

        Category savedCategory = categoryRepository.save(category);

        assertThat(savedCategory.getUser()).isEqualTo(user);
        assertThat(savedCategory.getName()).isEqualTo("Groceries");
        assertThat(savedCategory.getType()).isEqualTo(CategoryType.EXPENSE);
        assertThat(savedCategory.isDefault()).isTrue();
    }
}