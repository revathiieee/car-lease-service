package com.sogeti.carservice.model.response;

import com.sogeti.carservice.model.LeaseData;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Lease rate response model
 *
 * This class is used to map the response body to a java object
 * @Author: revathi
 */
@Data
public class LeaseRateResponse {

    /**
     * Car leaseData as LeaseData
     */
    private LeaseData leaseData;
    /**
     * Car leaseRate as BigDecimal
     */
    private BigDecimal leaseRate;
}
