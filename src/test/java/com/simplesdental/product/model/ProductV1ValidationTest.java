package com.simplesdental.product.model;

import com.simplesdental.product.model.Product.ProductV1;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;


public class ProductV1ValidationTest {

    private Validator validator;
    private ProductV1 productV1;

    @BeforeEach
    void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        productV1 = new ProductV1();
        productV1.setId(1L);
        productV1.setName("Test Product");
        productV1.setDescription("Test Description");
        productV1.setPrice(new BigDecimal("19.99"));
        productV1.setStatus(true);
        productV1.setCode("TP001");
        productV1.setCategory(new Category());

    }

    @Test
    void shouldCreateProductWithCorrectArguments() {

        Set<ConstraintViolation<ProductV1>> violations = validator.validate(productV1);
        Assertions.assertTrue(violations.isEmpty());
    }
    @Test
    void shouldNotCreateProductWithNullName() {
        productV1.setName(null);

        Set<ConstraintViolation<ProductV1>> violations = validator.validate(productV1);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    void shouldNotCreateProductWithEmptyName() {
        productV1.setName(" ");

        Set<ConstraintViolation<ProductV1>> violations = validator.validate(productV1);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateProductWithLongName() {
        productV1.setName("A".repeat(101));

        Set<ConstraintViolation<ProductV1>> violations = validator.validate(productV1);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateProductWithLongDescription() {
        productV1.setDescription("A".repeat(256));

        Set<ConstraintViolation<ProductV1>> violations = validator.validate(productV1);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateProductWithNegativeValue() {
        productV1.setPrice(BigDecimal.valueOf(-1));

        Set<ConstraintViolation<ProductV1>> violations = validator.validate(productV1);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateProductWithStatusNull() {
        productV1.setStatus(null);

        Set<ConstraintViolation<ProductV1>> violations = validator.validate(productV1);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotCreateProductWithCategoryNull() {
        productV1.setCategory(null);

        Set<ConstraintViolation<ProductV1>> violations = validator.validate(productV1);
        Assertions.assertFalse(violations.isEmpty());
    }

}
