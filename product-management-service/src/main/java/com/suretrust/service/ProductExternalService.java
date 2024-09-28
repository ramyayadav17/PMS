package com.suretrust.service;

import com.suretrust.dto.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductExternalService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductExternalService.class);

    @Value("${search.product.save}")
    private String saveProducts ;
    @Autowired
    WebClient webClient;

    public void saveOrUpdateProduct(ProductDTO product) {
        try {
            webClient.post()
                    .uri("/api/v1/products")
                    .body(Mono.just(product), ProductDTO.class)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

        } catch (Exception e) {
            LOG.error("Exception while saving product details: {}", product, e);
        }
    }
}
