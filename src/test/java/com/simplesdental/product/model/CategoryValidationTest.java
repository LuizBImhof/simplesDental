package com.simplesdental.product.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

public class CategoryValidationTest {

    private Validator validator;
    private Category category;

    @BeforeEach
    void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        category = new Category();
        category.setId(1L);
        category.setName("Categoria teste");
        category.setDescription("Descrição Categoria");

    }

    @Test
    void shouldCreateCategoryWithCorrectArguments(){
        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void shouldNotCreateCategoryWIthEmptyName(){
        category.setName("");

        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    void shouldNotCreateCategoryWIthNullName(){
        category.setName(null);

        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateCategoryWithLongDescription() {
        category.setDescription("A".repeat(256));

        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    void shouldCreateCategoryWithEmptyDescription() {
        category.setDescription("");

        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        Assertions.assertTrue(violations.isEmpty());
    }
}
