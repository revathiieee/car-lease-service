package com.sogeti.leaseservice.dto;

import lombok.Data;

/**
 * AuthenticationDTO is a DTO class for authentication.
 * It contains the email and password of a user.
 * It is used to transfer data between the controller and the service.
 * @Author revathi
 */
@Data
public class AuthenticationDTO {

    /**
     * Email of the user
     */
    private String email;

    /**
     * Password of the user
     */
    private String password;

}
