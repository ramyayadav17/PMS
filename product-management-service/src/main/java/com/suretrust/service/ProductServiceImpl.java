package com.suretrust.service;

import com.suretrust.dto.ProductDTO;
import com.suretrust.model.Brand;
import com.suretrust.model.Category;
import com.suretrust.model.Product;
import com.suretrust.repository.BrandRepository;
import com.suretrust.repository.CategoryRepository;
import com.suretrust.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final BrandRepository brandRepository;

    private final CategoryRepository categoryRepository;

    private final ProductExternalService externalService;


    @Override
    public void createProduct(ProductDTO productDTO) throws Exception {
        Brand brand = brandRepository.findByName(productDTO.getBrandName())
                .orElseGet(() -> brandRepository.save(
                        Brand.builder().name(productDTO.getBrandName()).build()
                ));

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = Product.builder()
                .name(productDTO.getName())
                .brand(brand)
                .category(category)
                .quantity(productDTO.getQuantity())
                .build();

        productRepository.save(product);
        productDTO.setCategoryName(category.getName());
        externalService.saveOrUpdateProduct(productDTO);

    }

    @Override
    public ProductDTO getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return new ProductDTO(
                product.getName(),
                product.getBrand().getName(),
                product.getCategory().getId(),
                product.getQuantity(),
                product.getCategory().getName()
        );
    }

    @Override
    public void updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Brand brand = brandRepository.findByName(productDTO.getBrandName())
                .orElseGet(() -> brandRepository.save(
                        Brand.builder().name(productDTO.getBrandName()).build()
                ));

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setName(productDTO.getName());
        product.setBrand(brand);
        product.setCategory(category);
        product.setQuantity(productDTO.getQuantity());
        productDTO.setCategoryName(category.getName());

        productRepository.save(product);
        externalService.saveOrUpdateProduct(productDTO);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> new ProductDTO(
                product.getName(),
                product.getBrand().getName(),
                product.getCategory().getId(),
                product.getQuantity(),
                product.getCategory().getName()
        )).collect(Collectors.toList());
    }

    @Override
    public void stockUpdate(Long productId,Long quantity) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Long remStock ;
        Product product = null;
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
            Long stock = product.getQuantity();
            remStock = stock - quantity;
            product.setQuantity(remStock);
            productRepository.save(product);
        }
    }

}
