package com.sogeti.carservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Period;
import lombok.NoArgsConstructor;

/**
 * Lease data model
 *
 * This class is used to map the request body to a java object
 * @Author: revathi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaseData {
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
    /**
     * Car as Car
     */
    private Car car;
}
