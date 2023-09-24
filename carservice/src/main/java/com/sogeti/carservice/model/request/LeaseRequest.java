package com.sogeti.carservice.model.request;

import com.sogeti.carservice.model.Car;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Period;

/**
 * Lease request model
 *
 * This class is used to map the request body to a java object
 * @Author: revathi
 */
@Data
public class LeaseRequest {

    /**
     * Car id as long
     */
    private Long id;
    /**
     * Car mileage as int
     */
    private int mileage;
    /**
     * Car durationInMonths as int
     */
    private int durationInMonths;
    /**
     * Car interestRate as BigDecimal
     */
    private BigDecimal interestRate;
}
