package com.simplesdental.product.service;

import com.simplesdental.product.model.Category;
import com.simplesdental.product.model.Product.ProductV1;
import com.simplesdental.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductV1ServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private ProductV1 productV1;

    @BeforeEach
    void setUp() {
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
    void shouldSaveProduct() {
        when(productRepository.save(any(ProductV1.class))).thenReturn(productV1);

        ProductV1 savedProductV1 = productService.save(productV1);

        assertThat(savedProductV1).isNotNull();
        assertThat(savedProductV1.getId()).isEqualTo(1L);
        assertThat(savedProductV1.getName()).isEqualTo("Test Product");
        verify(productRepository, times(1)).save(any(ProductV1.class));
    }

    @Test
    void shouldGetAllProducts() {
        Pageable pageable = PageRequest.of(0,10);
        Page<ProductV1> productPage = new PageImpl<>(List.of(productV1));
        when(productRepository.findAll(any(Pageable.class))).thenReturn(productPage);

        Page<ProductV1> products = productService.findAll(pageable);

        assertThat(products).isNotNull();
        assertThat(products.getContent().size()).isEqualTo(1);
        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    void shouldGetProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(productV1));

        Optional<ProductV1> foundProduct = productService.findById(1L);

        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getId()).isEqualTo(1L);
        assertThat(foundProduct.get().getName()).isEqualTo("Test Product");
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void shouldDeleteProductById() {
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteById(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }
}