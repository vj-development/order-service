package com.myproduct.order_service.feign;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Component
@Getter
@Setter
public class Product {
    private Integer id;

    private String title;

    private String sku;
    private Float price;
    private Integer stockLevel;
    private String description;
    private String imagePath;
}
