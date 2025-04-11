package com.simplesdental.product.service;

import com.simplesdental.product.model.Product.ProductV2;
import com.simplesdental.product.repository.ProductV2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductV2Service {

    private final ProductV2Repository productRepository;

    @Autowired
    public ProductV2Service(ProductV2Repository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductV2> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<ProductV2> findById(Long id) {
        return productRepository.findById(id);
    }

    public ProductV2 save(ProductV2 productV1) {
        return productRepository.save(productV1);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}