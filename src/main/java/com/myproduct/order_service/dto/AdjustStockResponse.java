package com.myproduct.order_service.dto;

import com.myproduct.order_service.feign.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdjustStockResponse {
    private List<Product> products;
    private String statusMessage;
    private int errorCode;
}
