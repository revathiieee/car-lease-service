package com.sogeti.customerservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Customer not found exception
 *
 * This exception is thrown when customer is not found
 * @Author: revathi
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException {

    /**
     * Constructor
     * @param message
     */
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
