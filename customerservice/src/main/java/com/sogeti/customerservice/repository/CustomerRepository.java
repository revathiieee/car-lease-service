package com.sogeti.customerservice.repository;

import com.sogeti.customerservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Customer repository
 * This is used to perform CRUD operations on customer table
 * @Author: revathi
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
