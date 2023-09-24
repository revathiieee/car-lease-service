package com.sogeti.customerservice.controller;

import com.sogeti.customerservice.model.Customer;
import com.sogeti.customerservice.model.request.CustomerRequest;
import com.sogeti.customerservice.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * Customer Controller
 *
 * This class is used to handle all the customer related requests
 * @Author: revathi
 */
@RestController
@RequestMapping("/customers")
@Slf4j
public class CustomerController {

    /**
     * Customer Service
     */
    private final CustomerService customerService;

    /**
     * Constructor
     * @param customerService
     */
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Method to create customer
     * @param customerRequest
     * @return ResponseEntity of customer
     */
    @PostMapping("/")
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerRequest customerRequest) {
        log.debug("Calling CustomerController.createCustomer {}", customerRequest);
        return ResponseEntity.status(CREATED).body(customerService.createCustomer(customerRequest));
    }

    /**
     * Method to get customer by id
     * @param id
     * @return ResponseEntity of customer
     */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    /**
     * Method to list all customers
     * @return List of customers
     */
    @GetMapping("/")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    /**
     * Method to update customer
     * @param id
     * @param customerRequest
     * @return ResponseEntity of customer
     */
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequest customerRequest) {
        log.debug("Calling CustomerController.updateCustomer {}", customerRequest);
        return ResponseEntity.ok(customerService.updateCustomer(id, customerRequest));
    }

     /**
      * Method to delete customer by id
      * @param id
      */
    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }

}
