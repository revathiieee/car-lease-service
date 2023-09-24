package com.sogeti.customerservice.model.request;

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
    private String name;
    /**
     * Customer street as string
     */
    private String street;
    /**
     * Customer house number as string
     */
    private String houseNumber;
    /**
     * Customer postal code as string
     */
    private String postalCode;
    /**
     * Customer city as string
     */
    private String city;
    /**
     * Customer email as string
     */
    private String email;
    /**
     * Customer phone number as string
     */
    private String phoneNumber;
}
