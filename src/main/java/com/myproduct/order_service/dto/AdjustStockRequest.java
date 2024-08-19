package com.myproduct.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdjustStockRequest {

    private String sku;
    private Integer quantity;
}

