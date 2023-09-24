package com.sogeti.customerservice.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Customer request model
 *
 * @author revathi
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    /**
     * Customer name as string
     */
    @NotNull
    private String name;
    /**
     * Customer street as string
     */
    @NotNull
    private String street;
    /**
     * Customer house number as string
     */
    @NotNull
    private String houseNumber;
    /**
     * Customer postal code as string
     */
    @NotNull
    private String postalCode;
    /**
     * Customer city as string
     */
    @NotNull
    private String city;
    /**
     * Customer email as string
     */
    @Email
    private String email;
    /**
     * Customer phone number as string
     */
    @NotNull
    @Pattern(regexp = "[1-9][0-9]{3}[a-zA-Z]{2}")
    private String phoneNumber;
}
