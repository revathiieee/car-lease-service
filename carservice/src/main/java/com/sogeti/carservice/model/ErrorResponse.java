package com.sogeti.carservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Error response model
 *
 * @author revathi
 */
@Data
@AllArgsConstructor
public class ErrorResponse {

    /**
     * Error message as string
     */
    private String message;
}
