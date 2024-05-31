package com.example.mysqljpaprac.sample.service;

import com.example.mysqljpaprac.sample.entity.Product;
import com.example.mysqljpaprac.sample.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public Product add(ProductDto productDto) {
        Product product = Product.builder()
                .prodName(productDto.getProdName())
                .prodDetail(productDto.getProdDetail())
                .build();
        Product saved = this.productRepository.save(product);

        return this.productRepository.findById(saved.getProdId()).get();
    }
}
