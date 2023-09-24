package com.sogeti.customerservice.controller;

import com.sogeti.customerservice.model.Customer;
import com.sogeti.customerservice.model.request.CustomerRequest;
import com.sogeti.customerservice.service.CustomerService;
import com.sogeti.customerservice.util.CustomerData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Test
    public void testCreateCustomer() {

        CustomerRequest customerRequest = CustomerData.getCustomerRequest();
        Customer customer = CustomerData.getCustomer();

        when(customerService.createCustomer(any(CustomerRequest.class))).thenReturn(customer);
        ResponseEntity<Customer> cusRes = customerController.createCustomer(customerRequest, null);
        assertEquals(cusRes.getStatusCodeValue(), 201);
        assertEquals(cusRes.getBody().getId(), 1L);
        assertEquals(cusRes.getBody().getName(), customerRequest.getName());
        assertEquals(cusRes.getBody().getStreet(), customerRequest.getStreet());
        assertEquals(cusRes.getBody().getHouseNumber(), customerRequest.getHouseNumber());
        assertEquals(cusRes.getBody().getPostalCode(), customerRequest.getPostalCode());
        assertEquals(cusRes.getBody().getCity(), customerRequest.getCity());
        assertEquals(cusRes.getBody().getEmail(), customerRequest.getEmail());
        assertEquals(cusRes.getBody().getPhoneNumber(), customerRequest.getPhoneNumber());
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> customerList = CustomerData.getCustomers();
        when(customerService.getAllCustomers()).thenReturn(customerList);
        List<Customer> customerListFinal = customerController.getAllCustomers();
        assertEquals(customerListFinal.size(), 2);
    }

    @Test
    public void testGetCustomerById() {
        CustomerRequest customerRequest = CustomerData.getCustomerRequest();
        Customer customer = CustomerData.getCustomer();
        when(customerService.getCustomerById(any(Long.class))).thenReturn(customer);
        ResponseEntity<Customer> cusRes = customerController.getCustomerById(1L);
        assertEquals(cusRes.getStatusCodeValue(), 200);
        assertEquals(cusRes.getBody().getId(), 1L);
        assertEquals(cusRes.getBody().getName(), customerRequest.getName());
        assertEquals(cusRes.getBody().getStreet(), customerRequest.getStreet());
        assertEquals(cusRes.getBody().getHouseNumber(), customerRequest.getHouseNumber());
        assertEquals(cusRes.getBody().getPostalCode(), customerRequest.getPostalCode());
        assertEquals(cusRes.getBody().getCity(), customerRequest.getCity());
        assertEquals(cusRes.getBody().getEmail(), customerRequest.getEmail());
        assertEquals(cusRes.getBody().getPhoneNumber(), customerRequest.getPhoneNumber());
    }


    @Test
    public void testUpdateCustomer() {

        CustomerRequest customerRequest = CustomerData.getCustomerRequest();
        customerRequest.setStreet("Main Street 2");

        Customer customer = CustomerData.getCustomer();
        customer.setStreet("Main Street 2");
        when(customerService.updateCustomer(any(Long.class), any(CustomerRequest.class))).thenReturn(customer);
        ResponseEntity<Customer> cusRes = customerController.updateCustomer(1L, customerRequest);
        assertEquals(cusRes.getStatusCodeValue(), 200);
        assertEquals(cusRes.getBody().getId(), 1L);
        assertEquals(cusRes.getBody().getName(), customerRequest.getName());
        assertEquals(cusRes.getBody().getStreet(), customerRequest.getStreet());
        assertEquals(cusRes.getBody().getHouseNumber(), customerRequest.getHouseNumber());
        assertEquals(cusRes.getBody().getPostalCode(), customerRequest.getPostalCode());
        assertEquals(cusRes.getBody().getCity(), customerRequest.getCity());
        assertEquals(cusRes.getBody().getEmail(), customerRequest.getEmail());
        assertEquals(cusRes.getBody().getPhoneNumber(), customerRequest.getPhoneNumber());
    }

    @Test
    public void testDeleteCustomerById() {
        customerController.deleteCustomerById(1L);
        verify(customerService, times(1)).deleteCustomerById(eq(1L));
    }

}
