package com.sogeti.carservice.service;

import com.sogeti.carservice.exception.CarNotFoundException;
import com.sogeti.carservice.model.Car;
import com.sogeti.carservice.model.LeaseData;
import com.sogeti.carservice.model.request.CarRequest;
import com.sogeti.carservice.model.request.LeaseRequest;
import com.sogeti.carservice.model.response.LeaseRateResponse;
import com.sogeti.carservice.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Period;
import java.util.List;

/**
 * Car service
 *
 * This class is used to perform CRUD operations on Car entity
 * @Author: revathi
 */
@Service
@Slf4j
public class CarService {

    /**
     * Car repository
     */
    private final CarRepository carRepository;

    /**
     * Constructor
     * @param carRepository
     */
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * Method to create car
     * @param carRequest
     * @return car
     */
    public Car createCar(CarRequest carRequest) {
        Car car = mapToCar(carRequest);
        return carRepository.save(car);
    }

    /**
     * Method to get all cars
     * @return list of cars
     */
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

   /**
     * Method to get car by id
     * @param id
     * @return car
     */
    public Car getCarById(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Car not found"));
    }

    /**
     * Method to update car
     * @param id
     * @param carRequest
     * @return car
     */
    public Car updateCar(Long id, CarRequest carRequest) {
        //Find car by id
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Car not found"));
        if(car == null) {
            return null;
        }
        car = mapToCar(carRequest);
        car.setId(id);
        return carRepository.save(car);
    }

    /**
     * Method to delete car by id
     * @param id
     */
    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }

    /**
     * Method to map car request to car
     * @param carRequest
     * @return car
     */
    private Car mapToCar(CarRequest carRequest) {
        return Car.builder()
            .make(carRequest.getMake())
            .model(carRequest.getModel())
            .version(carRequest.getVersion())
            .co2Emission(carRequest.getCo2Emission())
            .noOfDoors(carRequest.getNoOfDoors())
            .grossPrice(carRequest.getGrossPrice())
            .netPrice(carRequest.getNetPrice())
            .build();
    }

    /**
     * Method to get lease rate
     * @param leaseRequest
     * @return LeaseRateResponse
     */
    public LeaseRateResponse getLeaseRate(LeaseRequest leaseRequest) {
        log.debug("LeaseRequest: {}", leaseRequest);
        var car = carRepository.findById(leaseRequest.getId()).orElse(null);
        if(car == null) {
            return null;
        }


        BigDecimal leaseRate = calculateLeaseRate(leaseRequest, car);
        LeaseData leaseData = LeaseData.builder()
                .car(car)
                .mileage(leaseRequest.getMileage())
                .interestRate(leaseRequest.getInterestRate())
                .durationInMonths(leaseRequest.getDurationInMonths())
                .build();

        var leaseRateResponse = new LeaseRateResponse();
        leaseRateResponse.setLeaseData(leaseData);
        leaseRateResponse.setLeaseRate(leaseRate);
        log.debug("LeaseRateResponse: {}", leaseRateResponse);
        return leaseRateResponse;
    }

    /**
     * Method to calculate lease rate
     * @param leaseRequest
     * @param car
     * @return BigDecimal
     */
    private BigDecimal calculateLeaseRate(LeaseRequest leaseRequest, Car car) {
        BigDecimal mileageRatio = calculateMileageNetRatio(leaseRequest.getMileage(), leaseRequest.getDurationInMonths(), car.getNetPrice());
        BigDecimal interestRateRatio = calculateInterestRate(leaseRequest.getInterestRate(), car.getNetPrice());
        BigDecimal leaseRate = mileageRatio.add(interestRateRatio);
        return leaseRate;
    }

    /**
     * Method to calculate mileage net ratio
     * @param annualMileage
     * @param durationInMonths
     * @param netPrice
     * @return
     */
    private BigDecimal calculateMileageNetRatio(int annualMileage, int durationInMonths, BigDecimal netPrice) {
        BigDecimal monthlyMileage = new BigDecimal(annualMileage).divide(BigDecimal.valueOf(12), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal monthlyMileageRatio = monthlyMileage.multiply(BigDecimal.valueOf(durationInMonths)); //monthlyMileage.multiply(BigDecimal.valueOf(durationInMonths.getMonths()));
        return monthlyMileageRatio.divide(netPrice, 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Method to calculate interest rate
     * @param interestRate
     * @param netPrice
     * @return
     */
    private BigDecimal calculateInterestRate(BigDecimal interestRate, BigDecimal netPrice) {
        BigDecimal ratePercentage = interestRate.scaleByPowerOfTen(-2);
        BigDecimal carYearInterestRate = ratePercentage.multiply(netPrice);
        return carYearInterestRate.divide(BigDecimal.valueOf(12), 2, BigDecimal.ROUND_HALF_UP);
    }

}
