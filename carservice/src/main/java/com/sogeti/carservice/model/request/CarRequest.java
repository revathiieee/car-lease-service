package com.sogeti.carservice.model.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Car request model
 *
 * This class is used to map the request body to a java object
 * @Author: revathi
 */
@Data
public class CarRequest {
    /**
     * Car make as string
     */
    private String make;
    /**
     * Car model as string
     */
    private String model;
    /**
     * Car version as string
     */
    private String version;
    /**
     * Car fuel type as string
     */
    private String co2Emission;
    /**
     * Car fuel type as string
     */
    private int noOfDoors;
    /**
     * Car fuel type as string
     */
    private BigDecimal grossPrice;
    /**
     * Car fuel type as string
     */
    private BigDecimal netPrice;
}
