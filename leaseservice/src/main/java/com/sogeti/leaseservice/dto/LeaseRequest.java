package com.sogeti.leaseservice.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * LeaseRequest is a DTO class for lease request.
 * It contains the customerId, carId, mileage, durationInMonths and interestRate.
 * It is used to transfer data between the controller and the service.
 * @Author revathi
 */
@Data
public class LeaseRequest {

    /**
     * CustomerId of the lease request
     */
    private Long customerId;
    /**
     * CarId of the lease request
     */
    private Long carId;
    /**
     * Mileage of the lease request
     */
    private int mileage;
    /**
     * DurationInMonths of the lease request
     */
    private int durationInMonths;
    /**
     * InterestRate of the lease request
     */
    private BigDecimal interestRate;
}
