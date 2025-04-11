package com.simplesdental.product.repository;

import com.simplesdental.product.model.Product.ProductV1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductV1, Long> {

    @EntityGraph(attributePaths = {"category"})
    Page<ProductV1> findAll(Pageable pageable);
}