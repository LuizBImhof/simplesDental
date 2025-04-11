package com.simplesdental.product.service;

import com.simplesdental.product.model.Product.ProductV1;
import com.simplesdental.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductV1> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<ProductV1> findById(Long id) {
        return productRepository.findById(id);
    }

    public ProductV1 save(ProductV1 productV1) {
        return productRepository.save(productV1);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}