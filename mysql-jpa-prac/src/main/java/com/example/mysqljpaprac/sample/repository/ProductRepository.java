package com.example.mysqljpaprac.sample.repository;

import com.example.mysqljpaprac.sample.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepository {
    private final ProductMapper productMapper;
    private final ProductJpaRepository productJpaRepository;

    public Product save(Product product) {
        return this.productJpaRepository.save(product);
    }

    public Optional<Product> findById(Integer id) {
        return this.productMapper.findById(id);
    }
}
