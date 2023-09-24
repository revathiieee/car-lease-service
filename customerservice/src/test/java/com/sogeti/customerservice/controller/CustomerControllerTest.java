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
        customerController.createCustomer(customerRequest);

        assertEquals(customer.getId(), 1L);
        assertEquals(customer.getName(), customerRequest.getName());
        assertEquals(customer.getStreet(), customerRequest.getStreet());
        assertEquals(customer.getHouseNumber(), customerRequest.getHouseNumber());
        assertEquals(customer.getPostalCode(), customerRequest.getPostalCode());
        assertEquals(customer.getCity(), customerRequest.getCity());
        assertEquals(customer.getEmail(), customerRequest.getEmail());
        assertEquals(customer.getPhoneNumber(), customerRequest.getPhoneNumber());
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
        customerController.getCustomerById(1L);
        assertEquals(customer.getId(), 1L);
        assertEquals(customer.getName(), customerRequest.getName());
        assertEquals(customer.getStreet(), customerRequest.getStreet());
        assertEquals(customer.getHouseNumber(), customerRequest.getHouseNumber());
        assertEquals(customer.getPostalCode(), customerRequest.getPostalCode());
        assertEquals(customer.getCity(), customerRequest.getCity());
        assertEquals(customer.getEmail(), customerRequest.getEmail());
        assertEquals(customer.getPhoneNumber(), customerRequest.getPhoneNumber());
    }


    @Test
    public void testUpdateCustomer() {

        CustomerRequest customerRequest = CustomerData.getCustomerRequest();
        customerRequest.setStreet("Main Street 2");

        Customer customer = CustomerData.getCustomer();
        customer.setStreet("Main Street 2");
        when(customerService.updateCustomer(any(Long.class), any(CustomerRequest.class))).thenReturn(customer);
        customerController.updateCustomer(1L, customerRequest);

        assertEquals(customer.getId(), 1L);
        assertEquals(customer.getName(), customerRequest.getName());
        assertEquals(customer.getStreet(), customerRequest.getStreet());
        assertEquals(customer.getHouseNumber(), customerRequest.getHouseNumber());
        assertEquals(customer.getPostalCode(), customerRequest.getPostalCode());
        assertEquals(customer.getCity(), customerRequest.getCity());
        assertEquals(customer.getEmail(), customerRequest.getEmail());
        assertEquals(customer.getPhoneNumber(), customerRequest.getPhoneNumber());
    }

    @Test
    public void testDeleteCustomerById() {
        customerController.deleteCustomerById(1L);
        verify(customerService, times(1)).deleteCustomerById(eq(1L));
    }

}
