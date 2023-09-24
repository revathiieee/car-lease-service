package com.sogeti.customerservice.util;

import com.sogeti.customerservice.model.Customer;
import com.sogeti.customerservice.model.request.CustomerRequest;

import java.util.List;

public class CustomerData {

    public static Customer getCustomer() {
        return Customer.builder()
                .id(1L)
                .name("John Doe")
                .street("Main Street")
                .houseNumber("1")
                .postalCode("1234 AB")
                .city("Amsterdam")
                .email("john@test.com")
                .phoneNumber("0612345678")
                .build();
    }

    public static List<Customer> getCustomers() {
        return List.of(
                Customer.builder()
                        .id(1L)
                        .name("John Doe")
                        .street("Main Street")
                        .houseNumber("1")
                        .postalCode("1234 AB")
                        .city("Amsterdam")
                        .email("john@test.com")
                        .phoneNumber("0612345678")
                        .build(),
                Customer.builder()
                        .id(2L)
                        .name("Jane Doe")
                        .street("Main Street")
                        .houseNumber("2")
                        .postalCode("1234 AB")
                        .city("Amsterdam")
                        .email("jane@test.com")
                        .phoneNumber("0612345678")
                        .build());
    }

    public static CustomerRequest getCustomerRequest() {
        return CustomerRequest.builder()
                .name("John Doe")
                .street("Main Street")
                .houseNumber("1")
                .postalCode("1234 AB")
                .city("Amsterdam")
                .email("john@test.com")
                .phoneNumber("0612345678")
                .build();
    }

}
