package com.edu.apidemo.repositories;

import com.edu.apidemo.models.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findCategoryByKey(String key);
    Page<CategoryEntity> findAll(Pageable pageable);

}
