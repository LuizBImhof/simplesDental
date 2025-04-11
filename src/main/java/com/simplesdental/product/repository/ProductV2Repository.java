package com.simplesdental.product.repository;

import com.simplesdental.product.model.Product.ProductV2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductV2Repository extends JpaRepository<ProductV2, Long> {

    @EntityGraph(attributePaths = {"category"})
    Page<ProductV2> findAll(Pageable pageable);
}