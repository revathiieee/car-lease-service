package com.sogeti.customerservice.model;

import static jakarta.persistence.GenerationType.AUTO;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Customer entity
 *
 * @author revathi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    /**
     * Customer id
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Customer name as string
     */
    @Column(name = "name")
    private String name;
    /**
     * Customer street as string
     */
    @Column(name = "street")
    private String street;
    /**
     * Customer house number as string
     */
    @Column(name = "house_number")
    private String houseNumber;
    /**
     * Customer postal code as string
     */
    @Column(name = "postal_code")
    private String postalCode;
    /**
     * Customer city as string
     */
    @Column(name = "city")
    private String city;
    /**
     * Customer email as string
     */
    @Column(name="email")
    private String email;

    @Column(name="phone_number")
    private String phoneNumber;
}
