package com.myproduct.order_service.feign;

import com.myproduct.order_service.dto.AdjustStockRequest;
import com.myproduct.order_service.dto.AdjustStockResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Component
@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductInterface {

//    @GetMapping("/products")
//    public ResponseEntity<Iterable<Product>> index();
//
//    @PostMapping("/products/create")
//    public ResponseEntity<Product> create(Product product);

//    @PostMapping("/products/bulk_create")
//    public ResponseEntity<List<Product>> bulkCreate(@RequestBody List<Product> products);

    @GetMapping("/products/{sku}")
    public ResponseEntity<Product> show(@PathVariable String sku);

    @GetMapping("/products/in_stock")
    public ResponseEntity<Iterable<Product>> inStockProducts(Integer id);

    @PostMapping("/products/adjust_stock")
    public ResponseEntity<AdjustStockResponse> adjustStock(@RequestBody List<AdjustStockRequest> adjustStockRequests);

//    @GetMapping("/products/stock")
//    public ResponseEntity<Product> getProductWithStock(@RequestParam String sku, Integer stockLevel);

    @GetMapping("/products/{sku}/stock/{stockLevel}")
    public ResponseEntity<Product> getProductWithStock(@PathVariable String sku, @PathVariable Integer stockLevel);


    }
