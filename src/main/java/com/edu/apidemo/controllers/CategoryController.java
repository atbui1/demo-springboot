package com.edu.apidemo.controllers;


import com.edu.apidemo.models.CategoryEntity;
import com.edu.apidemo.models.ResponseObject;
import com.edu.apidemo.repositories.CategoryRepository;
import com.edu.apidemo.request.CategoryRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/api/v1/")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("category")
    ResponseEntity<?> getAllCategory() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        if (categories.size() < 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Categories is empty.", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "show all categories", categories)
        );
    }

    @GetMapping("category/{id}")
    ResponseEntity<?> getCategoryById(@PathVariable(value = "id") Long categoryId) {
        Optional category = categoryRepository.findById(categoryId);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Not found category id: " + categoryId, "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Found categories id: " + categoryId, category)
        );
    }

    @PostMapping("category")
    ResponseEntity<?> addNewCategory(@RequestBody CategoryRequest categoryRequest) {
        List<CategoryEntity> categories = categoryRepository.findCategoryByKey(categoryRequest.getKey().trim().toUpperCase());
        if (categories.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "exist category's key: " + categoryRequest.getKey().toUpperCase(), "")
            );
        }
        CategoryEntity category = new CategoryEntity(
                categoryRequest.getName(),
                categoryRequest.getKey().toUpperCase()
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "add new category successfully", categoryRepository.save(category))
        );
    }

    @PutMapping("category/{id}")
    ResponseEntity<?> updateCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable Long id) {
        Optional<CategoryEntity> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "a Not found category's id: " + id, "")
            );
        }
        category.map(i -> {
            i.setName(categoryRequest.getName());
            i.setKey(categoryRequest.getKey());
            return categoryRepository.save(i);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "a Update category successfully", category)
        );
    }

    @PutMapping("category/upsert/{id}")
    ResponseEntity<?> upsertCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable Long id) {
        CategoryEntity category = categoryRepository.findById(id).map(i -> {
            i.setName(categoryRequest.getName());
            i.setKey(categoryRequest.getKey().toUpperCase());
            return categoryRepository.save(i);
        }).orElseGet(() -> {
                    CategoryEntity newCategory  =  new CategoryEntity(
                            categoryRequest.getId(),
                            categoryRequest.getName(),
                            categoryRequest.getKey().toUpperCase()
                    );
                    return categoryRepository.save(newCategory);
                }
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "upsert category successfully", category)
        );
    }

    /** page */
    @GetMapping("category/page")
    ResponseEntity<?> showPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        try {
            List<CategoryEntity> categories = new ArrayList<CategoryEntity>();
            Pageable pageable = PageRequest.of(page, size);
//            Page<CategoryEntity> pageCategory = categoryRepository.findCategoryByKey(pageable);
            Page<CategoryEntity> pageCategory = categoryRepository.findAll(pageable);
            categories = pageCategory.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("tutorials", categories);
            response.put("currentPage", pageCategory.getNumber());
            response.put("totalItems", pageCategory.getTotalElements());
            response.put("totalPages", pageCategory.getTotalPages());
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "get category page successfully", response)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("failed", "exception : " + e, "")
            );
        }
    }
}
