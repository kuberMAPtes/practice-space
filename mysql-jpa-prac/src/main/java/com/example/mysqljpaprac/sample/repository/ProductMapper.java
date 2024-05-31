package com.example.mysqljpaprac.sample.repository;

import com.example.mysqljpaprac.sample.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface ProductMapper {

    void save(Product product);

    Optional<Product> findById(Integer id);
}
