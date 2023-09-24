package com.sogeti.leaseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * AuthenticationResponse is a DTO class for authentication response.
 * It contains the token of a user.
 * It is used to transfer data between the controller and the service.
 * @Author revathi
 */
@Data
@AllArgsConstructor
public class AuthenticationResponse {
    /**
     * Token of the user
     */
    private String token;
}
