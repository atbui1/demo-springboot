package com.edu.apidemo.repositories;

import com.edu.apidemo.models.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByProductName(String productName);
    Page<ProductEntity> findByPublished(boolean published, Pageable pageable);
    Page<ProductEntity> findByProductNameContaining(String productName, Pageable pageable);

    List<ProductEntity> findByCategoryId(Long categoryId);

}
