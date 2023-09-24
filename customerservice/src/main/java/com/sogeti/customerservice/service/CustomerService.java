package com.sogeti.customerservice.service;

import com.sogeti.customerservice.exception.CustomerNotFoundException;
import com.sogeti.customerservice.model.Customer;
import com.sogeti.customerservice.model.request.CustomerRequest;
import com.sogeti.customerservice.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Customer Service
 *
 * This class is used to perform CRUD operations on customer table
 * @Author: revathi
 */
@Service
@Slf4j
public class CustomerService {

    /**
     * Customer Repository
     */
    private final CustomerRepository customerRepository;

    /**
     * Constructor
     * @param customerRepository
     */
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Method to create customer
     * @param customerRequest
     * @return customer
     */
    public Customer createCustomer(CustomerRequest customerRequest) {
        Customer customer = mapToCustomer(customerRequest);
        return customerRepository.save(customer);
    }

    /**
     * Method to get customer by id
     * @param id
     * @return customer
     */
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    /**
     * Method to get all customers
     * @return list of customers
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }


    public Customer updateCustomer(Long id, CustomerRequest customerRequest) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        if(customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        customer = mapToCustomer(customerRequest);
        customer.setId(id);
        log.debug("updateCustomer Customer: {}", customer);
        return customerRepository.save(customer);
    }

    /**
     * Method to delete customer by id
     * @param id
     */
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    /**
     * Method to map customer request to customer
     * @param customerRequest
     * @return customer
     */
    private Customer mapToCustomer(CustomerRequest customerRequest) {
        return Customer.builder()
                .name(customerRequest.getName())
                .street(customerRequest.getStreet())
                .houseNumber(customerRequest.getHouseNumber())
                .postalCode(customerRequest.getPostalCode())
                .city(customerRequest.getCity())
                .email(customerRequest.getEmail())
                .phoneNumber(customerRequest.getPhoneNumber())
                .build();
    }
}
