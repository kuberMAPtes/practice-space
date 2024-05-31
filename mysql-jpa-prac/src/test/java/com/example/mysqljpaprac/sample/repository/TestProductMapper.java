package com.example.mysqljpaprac.sample.repository;

import com.example.mysqljpaprac.sample.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestProductMapper {

    @Autowired
    ProductMapper productMapper;

    @Test
    void test() {
        Product saved = Product.builder()
                .prodName("sample-product")
                .prodDetail("sample-detail")
                .build();
        System.out.println(saved);
        this.productMapper.save(saved);
        System.out.println(saved);
    }
}