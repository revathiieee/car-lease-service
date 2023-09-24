package com.sogeti.carservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Car model
 *
 * This class is used to map the car table to a java object
 * @Author: revathi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "car")
public class Car {
    /**
     * Car id as long
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Car make as string
     */
    @Column(name = "make")
    private String make;
    /**
     * Car model as string
     */
    @Column(name = "model")
    private String model;
    /**
     * Car version as string
     */
    @Column(name="version")
    private String version;
    /**
     * Car fuel type as string
     */
    @Column(name="co2_emission")
    private String co2Emission;
    /**
     * Car noOfDoorse as int
     */
    @Column(name="no_of_doors")
    private int noOfDoors;
    /**
     * Car grossPrice as BigDecimal
     */
    @Column(name="gross_price")
    private BigDecimal grossPrice;
    /**
     * Car netPrice as BigDecimal
     */
    @Column(name="net_price")
    private BigDecimal netPrice;
}
