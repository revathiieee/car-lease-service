package com.sogeti.leaseservice.service;

import com.sogeti.leaseservice.client.CarClient;
import com.sogeti.leaseservice.client.CustomerClient;
import com.sogeti.leaseservice.dto.LeaseContractData;
import com.sogeti.leaseservice.dto.LeaseRequest;
import com.sogeti.leaseservice.swagger.car.model.LeaseRateResponse;
import com.sogeti.leaseservice.swagger.customer.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The type Lease service.
 *
 * This class is used to call the customer and car services.
 * @Author: revathi
 */
@Service
@Slf4j
public class LeaseServiceImpl {

    /**
     * The Customer client.
     */
    private final CustomerClient customerClient;

    /**
     * The Car client.
     */
    private final CarClient carClient;

    /**
     * Instantiates a new Lease service.
     *
     * @param customerClient the customer client
     * @param carClient      the car client
     */
    public LeaseServiceImpl(CustomerClient customerClient, CarClient carClient) {
        this.customerClient = customerClient;
        this.carClient = carClient;
    }

    /**
     * Gets customer by id.
     *
     * @param leaseRequest the lease request
     * @param token        the token
     * @return the customer by id
     */
    public LeaseContractData getCustomerById(LeaseRequest leaseRequest, String token) {
        log.info("Calling LeaseServiceImpl.customerId {} and carId {}", leaseRequest.getCustomerId(), leaseRequest.getCarId());
        LeaseContractData leaseContractData = new LeaseContractData();
        Customer customer = customerClient.getCustomerById(leaseRequest.getCustomerId(), token);
        LeaseRateResponse leaseRateResponse = carClient.getLeaseRate(leaseRequest, token);
        leaseContractData.setCustomer(customer);
        leaseContractData.setLeaseRateResponse(leaseRateResponse);
        log.debug("LeaseContractData: {}", leaseContractData);
        return leaseContractData;
    }
}
