package com.simplesdental.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplesdental.product.model.Category;
import com.simplesdental.product.model.Product.ProductV1;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductV1ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

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

    @WithMockUser(username = "teste@teste.com", roles = "ADMIN")
    @Test
    void shouldNotCreateProductV1() throws Exception {
        when(productService.save(any(ProductV1.class))).thenReturn(productV1);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productV1)))
                .andExpect(status().isMethodNotAllowed());
    }

    @WithMockUser(username = "teste@teste.com", roles = "USER")
    @Test
    void shouldGetAllProducts() throws Exception {

        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductV1> productPage = new PageImpl<>(List.of(productV1), pageable, 1);
        when(productService.findAll(any(Pageable.class))).thenReturn(productPage);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(productV1.getId()))
                .andExpect(jsonPath("$.content[0].name").value(productV1.getName()))
                .andExpect(jsonPath("$.content",hasSize(1)));
    }


    @WithMockUser(username = "teste@teste.com", roles = "USER")
    @Test
    void shouldGetProductById() throws Exception {
        when(productService.findById(1L)).thenReturn(Optional.of(productV1));

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productV1.getId()))
                .andExpect(jsonPath("$.name").value(productV1.getName()));
    }

    @WithMockUser(username = "teste@teste.com", roles = "USER")
    @Test
    void shouldReturn404WhenGetProductByIdNotFound() throws Exception {
        when(productService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "teste@teste.com", roles = "ADMIN")
    @Test
    void shouldUpdateProduct() throws Exception {
        when(productService.findById(1L)).thenReturn(Optional.of(productV1));
        when(productService.save(any(ProductV1.class))).thenReturn(productV1);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productV1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productV1.getId()))
                .andExpect(jsonPath("$.name").value(productV1.getName()));
    }

    @WithMockUser(username = "teste@teste.com", roles = "ADMIN")
    @Test
    void shouldReturn404WhenUpdateProductNotFound() throws Exception {
        when(productService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productV1)))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "teste@teste.com", roles = "ADMIN")
    @Test
    void shouldDeleteProduct() throws Exception {
        when(productService.findById(1L)).thenReturn(Optional.of(productV1));
        doNothing().when(productService).deleteById(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(username = "teste@teste.com", roles = "ADMIN")
    @Test
    void shouldReturn404WhenDeleteProductNotFound() throws Exception {
        when(productService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNotFound());
    }
}