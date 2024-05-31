package com.example.mysqljpaprac.sample.service;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ProductDto {
    private Integer prodId;
    private String prodName;
    private String prodDetail;
}
