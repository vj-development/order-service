package com.myproduct.order_service.services;

import com.myproduct.order_service.dto.AdjustStockRequest;
import com.myproduct.order_service.dto.AdjustStockResponse;
import com.myproduct.order_service.feign.Product;
import com.myproduct.order_service.feign.ProductInterface;
import com.myproduct.order_service.models.Order;
import com.myproduct.order_service.models.OrderItem;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

        @Autowired
        private ProductInterface productInterface;
        public Optional<Product> getProduct(String sku, Integer stockLevel) {
                ResponseEntity<Product> response = productInterface.getProductWithStock(sku, stockLevel);
                return Optional.ofNullable(response.getBody());
        }

        public AdjustStockResponse adjustStock(Order order){
                List<AdjustStockRequest> adjustStockRequest = new ArrayList<>();
                for(OrderItem orderItem : order.getOrderItems()) {
                        AdjustStockRequest adjustStockRequest1 = new AdjustStockRequest(orderItem.getSku(), orderItem.getQuantity());
                        adjustStockRequest.add(adjustStockRequest1);
                }
                try{
                        return productInterface.adjustStock(adjustStockRequest).getBody();

                } catch (FeignException.FeignClientException e) {
                        AdjustStockResponse adjustStockResponse = new AdjustStockResponse();
                        adjustStockResponse.setErrorCode(500);
                        adjustStockResponse.setStatusMessage(e.getMessage());
                        return adjustStockResponse;
                }

        }
}
