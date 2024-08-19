package com.myproduct.order_service.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Getter
@Setter
public class InvalidSkuException extends Throwable {

    private String message;
    public InvalidSkuException(String s) {
        setMessage(s);
    }
}
