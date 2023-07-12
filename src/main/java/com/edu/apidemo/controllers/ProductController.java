package com.edu.apidemo.controllers;

import com.edu.apidemo.models.ProductEntity;
import com.edu.apidemo.models.ResponseObject;
import com.edu.apidemo.repositories.CategoryRepository;
import com.edu.apidemo.repositories.ProductRepository;
import com.edu.apidemo.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8090")
@RestController
@RequestMapping(path = "/api/v1/")
@EnableJpaAuditing
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * get all product
     * http://localhost:8090/api/v1/product/all
     */
    @GetMapping("product/all")
    ResponseEntity<?> getAllProduct() {
        List<ProductEntity> products = productRepository.findAll();
        if (products.size() < 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "products is empty..!!", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Get products successfully", products)
        );
    }
    @GetMapping("product/all1")
    ResponseEntity<?> getAllProduct1() {
        List<ProductResponse> productResponses = new ArrayList<>();
        List<ProductEntity> products = productRepository.findAll();
        if (products.size() < 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "products is empty..!!", "")
            );
        }
        products.forEach((x) -> {
            ProductResponse productResponse = new ProductResponse(
                    x.getId(),
                    x.getProductName(),
                    x.getYearOfBirth(),
                    x.getPrice(),
                    x.getDescription(),
                    x.isPublished(),
                    x.getAge(),
                    x.getCategory()
            );
             productResponses.add(productResponse);
        });
        System.out.println("index qaz 11: " + productResponses.get(0));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Get products successfully", productResponses)
        );
    }

    /**
     * get product by product id
     * http://localhost:8090/api/v1/product/{id}
     */
    @GetMapping("product/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable long id) {
        Optional<ProductEntity> foundProduct = productRepository.findById(id);
        if(foundProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query product successfully", foundProduct)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "not found product id: " + id, "")
            );
        }
    }

    /** update*/
    @PutMapping("product/update/categoryId={categoryId}/productId={productId}")
    ResponseEntity<ResponseObject> insertNewProduct(@RequestBody ProductEntity newProduct,
                                                    @PathVariable(value = "categoryId") Long categoryId,
                                                    @PathVariable(value = "productId") Long productId) {
        if (!categoryRepository.existsById(categoryId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Not found category's id: " + categoryId, "")
            );
        }
//        if(productRepository.findByProductName(newProduct.getProductName().trim()).size() > 0) {
//            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
//                    new ResponseObject("failed", "Product name is already exist", "")
//            );
//        }

        if (!productRepository.existsById(productId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Not found product's id: " + productId, "")
            );
        }

        Optional<ProductEntity> product = productRepository.findById(productId).map(
                (item) -> {
                    item.setProductName(newProduct.getProductName());
                    item.setYearOfBirth(newProduct.getYearOfBirth());
                    item.setPrice(newProduct.getPrice());
                    item.setDescription(newProduct.getDescription());
                    item.setPublished(newProduct.isPublished());
                    item.setCategory(categoryRepository.findById(categoryId).get());
                    return productRepository.save(item);
                }
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "update product successfully add new ", product)
        );
    }

    /**
     * upsert
     */
    @PutMapping("product/upsert/categoryId={categoryId}/productId={productId}")
    ResponseEntity<?> updateCategoryForProduct(@RequestBody ProductEntity newProduct,
                                               @PathVariable(value = "categoryId") Long categoryId,
                                               @PathVariable(value = "productId") Long productId) {
        if (!categoryRepository.existsById(categoryId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Not found category's id: " + categoryId, "")
            );
        }

        if (!productRepository.existsById(productId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Not found product's id: " + productId, "")
            );
        }

        ProductEntity product = productRepository.findById(productId).map(
                (item) -> {
                    item.setProductName(newProduct.getProductName());
                    item.setYearOfBirth(newProduct.getYearOfBirth());
                    item.setPrice(newProduct.getPrice());
                    item.setDescription(newProduct.getDescription());
                    item.setPublished(newProduct.isPublished());
                    item.setCategory(categoryRepository.findById(categoryId).get());
                    return productRepository.save(item);
                }
        ).orElseGet(
                () -> {
                    newProduct.setId(productId);
                    return productRepository.save(newProduct);
                }
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "upsert product successfully add new ", product)
        );
    }

    /**
     * update NO category
     */
    @PutMapping("product/update/{id}")
    ResponseEntity<ResponseObject> upsertProduct(@RequestBody ProductEntity newProduct, @PathVariable long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Not found product's id: " + id, "")
            );
        }
        Optional<ProductEntity> updateProduct =  productRepository.findById(id)
                .map(product -> {
                    product.setProductName(newProduct.getProductName());
                    product.setYearOfBirth(newProduct.getYearOfBirth());
                    product.setPrice(newProduct.getPrice());
                    product.setDescription(newProduct.getDescription());
                    product.setPublished(newProduct.isPublished());
                    return productRepository.save(product);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "upsert product successfully", updateProduct)
        );
    }

    /**
     * upsert No category
     */
    /**
    @PutMapping("product/upsert/categoryId={categoryId}/productId={productId}")
    ResponseEntity<?> upsertCategoryForProduct(@RequestBody ProductEntity newProduct,
                                               @PathVariable(value = "categoryId") Long categoryId,
                                               @PathVariable(value = "productId") Long productId) {
        if (!categoryRepository.existsById(categoryId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Not found category's id: " + categoryId, "")
            );
        }

        if (!productRepository.existsById(productId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Not found product's id: " + productId, "")
            );
        }

        ProductEntity product = productRepository.findById(productId).map(
                (item) -> {
                    item.setProductName(newProduct.getProductName());
                    item.setYearOfBirth(newProduct.getYearOfBirth());
                    item.setPrice(newProduct.getPrice());
                    item.setDescription(newProduct.getDescription());
                    item.setPublished(newProduct.isPublished());
                    item.setCategoryEntity(categoryRepository.findById(categoryId).get());
                    return productRepository.save(item);
                }
        ).orElseGet(
                () -> {
                    newProduct.setId(productId);
                    return productRepository.save(newProduct);
                }
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "upsert product successfully add new ", product)
        );
    }
*/

    @DeleteMapping("product/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable long id) {
        boolean isExist = productRepository.existsById(id);
        if(isExist) {
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "delete product successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "cannot find product id: " + id, "")
        );
    }

    /** page */
    @GetMapping("product/page")
    ResponseEntity<?> getProductByPage(
//    ResponseEntity<Map<String, Object>> getProductByPage(
            @RequestParam(required = false) String productName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        try {
            List<ProductEntity> products = new ArrayList<ProductEntity>();
            Pageable pageable = PageRequest.of(page, size);
            Page<ProductEntity> pageProduct;
            if (productName == null) {
                pageProduct = productRepository.findAll(pageable);
            } else {
                pageProduct = productRepository.findByProductNameContaining(productName, pageable);
            }
            products = pageProduct.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("products", products);
            response.put("currentPage", pageProduct.getNumber());
            response.put("totalItems", pageProduct.getTotalElements());
            response.put("totalPages", pageProduct.getTotalPages());
//            return new ResponseEntity<>(response, HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "get products in page successfully", response)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    new ResponseObject("failed", "exception : " + e, "")
            );
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("product/published")
    ResponseEntity<?> getProductPageByPublish(
            @RequestParam(required = false) String productName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
       try {
           List<ProductEntity> products = new ArrayList<ProductEntity>();
           Pageable pageable = PageRequest.of(page, size);
           Page<ProductEntity> pageProduct = productRepository.findByPublished(true, pageable);
           products = pageProduct.getContent();
           Map<String, Object> response = new HashMap<>();
           response.put("tutorials", products);
           response.put("currentPage", pageProduct.getNumber());
           response.put("totalItems", pageProduct.getTotalElements());
           response.put("totalPages", pageProduct.getTotalPages());

           return ResponseEntity.status(HttpStatus.OK).body(
                   new ResponseObject("ok", "get products by published in page successfully", response)
           );
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("failed", "exception : " + e, "")
            );
       }
    }

    /** many to one */
    /**
     * get product by category id
     * http://localhost:8090/api/v1/category/{categoryId}/product
     */
    @GetMapping("category/{categoryId}/product")
    ResponseEntity<?> getAllProductByCategoryId(@PathVariable(value = "categoryId") Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Not found PRODUCT category id: " + categoryId, "")
            );
        }
        List<ProductEntity> products = productRepository.findByCategoryId(categoryId);
        if (products.size() < 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Not found product with category' id:  " + categoryId, "")
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Found product by category id: " + categoryId, products)
        );
    }
    @PostMapping("category/{categoryId}/product")
    ResponseEntity<?> addNewProductInCategory(@PathVariable(value = "categoryId") Long categoryId,
                                              @RequestBody ProductEntity productRequest) {
        if (!categoryRepository.existsById(categoryId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Not found category id: " + categoryId, "")
            );
        }
        if (productRepository.findByProductName(productRequest.getProductName().trim()).size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Exist product's name : " + productRequest.getProductName().trim(), "")
            );
        }
        Optional<ProductEntity> product = categoryRepository.findById(categoryId).map(
                (item) -> {
                    productRequest.setCategory(item);
                    return productRepository.save(productRequest);
                }
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("failed", "add new pro duct with category successfully " + categoryId, product)
        );
    }
}
