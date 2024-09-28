package com.suretrust.service;

import com.suretrust.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    void createProduct(ProductDTO productDTO) throws Exception;

    ProductDTO getProduct(Long productId);

    void updateProduct(Long productId, ProductDTO productDTO);

    void deleteProduct(Long productId);

    List<ProductDTO> getAllProducts();

    void stockUpdate(Long productId,Long quantity) ;
}
