package com.sogeti.customerservice.service;

import com.sogeti.customerservice.exception.CustomerNotFoundException;
import com.sogeti.customerservice.model.Customer;
import com.sogeti.customerservice.model.request.CustomerRequest;
import com.sogeti.customerservice.repository.CustomerRepository;
import com.sogeti.customerservice.util.CustomerData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void testCreateCustomer() {
        Customer customer = CustomerData.getCustomer();
        CustomerRequest customerRequest = CustomerData.getCustomerRequest();
        when(customerRepository.save(any(Customer.class))).thenReturn(CustomerData.getCustomer());
        customerService.createCustomer(customerRequest);

        assertNotNull(customer);
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
    public void testGetCustomerById() {
        Customer customer = CustomerData.getCustomer();
        when(customerRepository.findById(1L)).thenReturn(java.util.Optional.of(customer));
        customerService.getCustomerById(1L);

        assertNotNull(customer);
        assertEquals(customer.getId(), 1L);
        assertEquals(customer.getName(), "John Doe");
        assertEquals(customer.getStreet(), "Main Street");
        assertEquals(customer.getHouseNumber(), "1");
        assertEquals(customer.getPostalCode(), "1234 AB");
        assertEquals(customer.getCity(), "Amsterdam");
        assertEquals(customer.getEmail(), "john@test.com");
        assertEquals(customer.getPhoneNumber(), "0612345678");
    }

    @Test
    public void testGetCustomerByIdNotFound() {
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getCustomerById(3L);
        });
        assertEquals("Customer not found", exception.getMessage());
    }

    @Test
    public void testUpdateCustomerNotFound() {
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.updateCustomer(3L, CustomerData.getCustomerRequest());
        });
        assertEquals("Customer not found", exception.getMessage());
    }

    @Test
    public void testGetAllCustomers() {
        Customer customer = CustomerData.getCustomer();
        when(customerRepository.findAll()).thenReturn(java.util.List.of(customer));
        customerService.getAllCustomers();

        assertNotNull(customer);
        assertEquals(customer.getId(), 1L);
        assertEquals(customer.getName(), "John Doe");
        assertEquals(customer.getStreet(), "Main Street");
        assertEquals(customer.getHouseNumber(), "1");
        assertEquals(customer.getPostalCode(), "1234 AB");
        assertEquals(customer.getCity(), "Amsterdam");
        assertEquals(customer.getEmail(), "john@test.com");
        assertEquals(customer.getPhoneNumber(), "0612345678");
    }

    @Test
    public void testUpdateCustomer() {
        CustomerRequest customerRequest = CustomerData.getCustomerRequest();
        customerRequest.setStreet("Main Street 2");

        Customer customer = CustomerData.getCustomer();
        Customer customerUpdated = CustomerData.getCustomer();
        customerUpdated.setStreet("Main Street 2");

        when(customerRepository.findById(1L)).thenReturn(java.util.Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customerUpdated);

        customerService.updateCustomer(1L, customerRequest);

        assertNotNull(customerUpdated);
        assertEquals(customerUpdated.getId(), 1L);
        assertEquals(customerUpdated.getName(), customerRequest.getName());
        assertEquals(customerUpdated.getStreet(), customerRequest.getStreet());
        assertEquals(customerUpdated.getHouseNumber(), customerRequest.getHouseNumber());
        assertEquals(customerUpdated.getPostalCode(), customerRequest.getPostalCode());
        assertEquals(customerUpdated.getCity(), customerRequest.getCity());
        assertEquals(customerUpdated.getEmail(), customerRequest.getEmail());
        assertEquals(customerUpdated.getPhoneNumber(), customerRequest.getPhoneNumber());
    }

    @Test
    public void testDeleteCustomerById() {
        customerService.deleteCustomerById(1L);
        verify(customerRepository, times(1)).deleteById(eq(1L));
    }

}
