package com.simplesdental.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplesdental.product.model.Product;
import com.simplesdental.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("19.99"));
        product.setStatus(true);
        product.setCode("TP001");
    }

    @Test
    void shouldCreateProduct() throws Exception {
        when(productService.save(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()));
    }

    @Test
    void shouldGetAllProducts() throws Exception {

        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(List.of(product), pageable, 1);
        when(productService.findAll(any(Pageable.class))).thenReturn(productPage);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(product.getId()))
                .andExpect(jsonPath("$.content[0].name").value(product.getName()))
                .andExpect(jsonPath("$.content",hasSize(1)));
    }

    @Test
    void shouldGetProductById() throws Exception {
        when(productService.findById(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()));
    }

    @Test
    void shouldReturn404WhenGetProductByIdNotFound() throws Exception {
        when(productService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        when(productService.findById(1L)).thenReturn(Optional.of(product));
        when(productService.save(any(Product.class))).thenReturn(product);

        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()));
    }

    @Test
    void shouldReturn404WhenUpdateProductNotFound() throws Exception {
        when(productService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        when(productService.findById(1L)).thenReturn(Optional.of(product));
        doNothing().when(productService).deleteById(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenDeleteProductNotFound() throws Exception {
        when(productService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNotFound());
    }
}