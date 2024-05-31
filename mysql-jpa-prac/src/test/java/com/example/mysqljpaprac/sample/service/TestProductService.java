package com.example.mysqljpaprac.sample.service;

import com.example.mysqljpaprac.sample.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestProductService {

    @Autowired
    ProductService productService;

    @Test
    void test() {
        Product add = this.productService.add(ProductDto.builder()
                .prodName("sample-name")
                .prodDetail("sample-detail")
                .build());
        System.out.println(add);
    }
}