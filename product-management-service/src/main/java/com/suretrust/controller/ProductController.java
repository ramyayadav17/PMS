package com.suretrust.controller;

import com.suretrust.dto.ProductDTO;
import com.suretrust.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            productService.createProduct(productDTO);
            return ResponseEntity.ok("Product created successfully");
        } catch (Exception e) {
            LOGGER.error("Exception occurs while creating product ");
            throw new RuntimeException("Exception occurs while creating product");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        try {
            ProductDTO productDTO = productService.getProduct(id);
            return ResponseEntity.ok(productDTO);
        } catch (Exception e) {
            throw new RuntimeException("Exception occurs while getting product details");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        try {
            productService.updateProduct(id, productDTO);
            return ResponseEntity.ok("Product updated successfully");
        } catch (Exception e) {
            throw new RuntimeException("Exception occurs while updating product");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (Exception e) {
            throw new RuntimeException("Exception occurs while deleting product details");
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        try {
            List<ProductDTO> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Exception while getting all products");
        }
    }
    @PutMapping("/api/v1/products/{productId}/{quantity}/stockUpdate")
    public void stockUpdate(Long prductId, Long quantity) {
        try {
            productService.stockUpdate(prductId, quantity);
        } catch (Exception e) {
            throw new RuntimeException("Exception while updating the stock");
        }
    }
}
