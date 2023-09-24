package com.sogeti.leaseservice.dto;

import com.sogeti.leaseservice.swagger.car.model.LeaseData;
import com.sogeti.leaseservice.swagger.car.model.LeaseRateResponse;
import com.sogeti.leaseservice.swagger.customer.model.Customer;
import lombok.Data;

/**
 * LeaseContractData is a DTO class for lease contract data.
 * It contains the customer and lease rate response.
 * It is used to transfer data between the controller and the service.
 * @Author revathi
 */
@Data
public class LeaseContractData {

    /**
     * Customer of the lease contract
     */
    private Customer customer;

    /**
     * Lease rate response of the lease contract
     */
    private LeaseRateResponse leaseRateResponse;
}
