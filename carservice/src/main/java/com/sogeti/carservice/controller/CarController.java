package com.sogeti.carservice.controller;

import com.sogeti.carservice.model.Car;
import com.sogeti.carservice.model.request.CarRequest;
import com.sogeti.carservice.model.request.LeaseRequest;
import com.sogeti.carservice.model.response.LeaseRateResponse;
import com.sogeti.carservice.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * Car Controller
 *
 * This class is used to handle all the car related requests
 * @Author: revathi
 */
@RestController
@RequestMapping("/cars")
@Slf4j
public class CarController {

    /**
     * Car Service
     */
    private final CarService carService;

    /**
     * Constructor
     * @param carService
     */
    public CarController(CarService carService) {
        this.carService = carService;
    }

    /**
     * Method for getting lease rate
     * @param leaseRequest
     * @return ResponseEntity of LeaseRateResponse
     */
    @PostMapping("/leaserate")
    public ResponseEntity<LeaseRateResponse> getLeaseRate(@RequestBody LeaseRequest leaseRequest, @RequestHeader(value = "Authorization", required = false) String token) {
        log.debug("Calling CarController.getLeaseRate {}", leaseRequest);
        return ResponseEntity.ok(carService.getLeaseRate(leaseRequest));
    }

    /**
     * Method to create car
     * @param carRequest
     * @return ResponseEntity of car
     */
    @PostMapping("/")
    public ResponseEntity<Car> createCar(@RequestBody CarRequest carRequest) {
        log.debug("Calling CarController.createCar {}", carRequest);
        return ResponseEntity.status(CREATED).body(carService.createCar(carRequest));
    }

    /**
     * Method to get car by id
     * @param id
     * @return ResponseEntity of car
     */
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    /**
     * Method to list all cars
     * @return List of cars
     */
    @GetMapping("/")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    /**
     * Method to update car
     * @param id
     * @param carRequest
     * @return ResponseEntity of car
     */
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody CarRequest carRequest) {
        return ResponseEntity.ok(carService.updateCar(id, carRequest));
    }

    /**
     * Method to delete car by id
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteCarById(@PathVariable Long id) {
        carService.deleteCarById(id);
    }


}
