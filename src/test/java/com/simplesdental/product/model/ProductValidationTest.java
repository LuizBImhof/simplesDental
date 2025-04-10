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


public class ProductValidationTest {

    private Validator validator;
    private Product product;

    @BeforeEach
    void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("19.99"));
        product.setStatus(true);
        product.setCode("TP001");
        product.setCategory(new Category());

    }

    @Test
    void shouldCreateProductWithCorrectArguments() {

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        Assertions.assertTrue(violations.isEmpty());
    }
    @Test
    void shouldNotCreateProductWithNullName() {
        product.setName(null);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    void shouldNotCreateProductWithEmptyName() {
        product.setName(" ");

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateProductWithLongName() {
        product.setName("A".repeat(101));

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateProductWithLongDescription() {
        product.setDescription("A".repeat(256));

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateProductWithNegativeValue() {
        product.setPrice(BigDecimal.valueOf(-1));

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateProductWithStatusNull() {
        product.setStatus(null);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateProductWithCategoryNull() {
        product.setCategory(null);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        Assertions.assertFalse(violations.isEmpty());
    }

}
