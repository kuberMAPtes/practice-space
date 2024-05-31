package com.example.mysqljpaprac.sample.repository;

import com.example.mysqljpaprac.sample.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Integer> {
}
