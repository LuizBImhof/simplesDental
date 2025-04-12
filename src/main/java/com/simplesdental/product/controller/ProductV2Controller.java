package com.simplesdental.product.controller;

import com.simplesdental.product.controller.docs.ProductV2SwaggerDefinition;
import com.simplesdental.product.model.Product.ProductV2;
import com.simplesdental.product.service.ProductV2Service;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/products")
public class ProductV2Controller implements ProductV2SwaggerDefinition {

    private final ProductV2Service productService;

    @Autowired
    public ProductV2Controller(ProductV2Service productService) {
        this.productService = productService;
    }

    @GetMapping
    @Transactional
    public Page<ProductV2> getAllProducts(@PageableDefault(size = 10, sort = "name")Pageable pageable) {
        Page<ProductV2> products = productService.findAll(pageable);
        products.forEach(product -> {
            if (product.getCategory() != null) {
                Hibernate.initialize(product.getCategory());
            }
        });
        return products;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductV2> getProductById(@PathVariable Long id) {
        return productService.findById(id)
                .map(product -> {
                    if (product.getCategory() != null) {
                        Hibernate.initialize(product.getCategory());
                    }
                    return ResponseEntity.ok(product);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductV2 createProduct(@Valid @RequestBody ProductV2 productV2) {
        return productService.save(productV2);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductV2> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductV2 productV2) {
        return productService.findById(id)
                .map(existingProduct -> {
                    productV2.setId(id);
                    return ResponseEntity.ok(productService.save(productV2));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        return productService.findById(id)
                .map(product -> {
                    productService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}