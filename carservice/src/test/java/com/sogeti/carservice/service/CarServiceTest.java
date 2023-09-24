package com.sogeti.carservice.service;

import com.sogeti.carservice.exception.CarNotFoundException;
import com.sogeti.carservice.model.Car;
import com.sogeti.carservice.model.request.CarRequest;
import com.sogeti.carservice.model.request.LeaseRequest;
import com.sogeti.carservice.model.response.LeaseRateResponse;
import com.sogeti.carservice.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import util.CarData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @InjectMocks
    private CarService carService;
    @Mock
    private CarRepository carRepository;

    @Test
    public void testCreateCar() {

        CarRequest carRequest = CarData.getCarRequest();

        Car car = CarData.getCarData();
        when(carRepository.save(any(Car.class))).thenReturn(car);
        carService.createCar(carRequest);

        assertEquals(car.getId(), 1L);
        assertEquals(car.getMake(), carRequest.getMake());
        assertEquals(car.getModel(), carRequest.getModel());
        assertEquals(car.getVersion(), carRequest.getVersion());
        assertEquals(car.getCo2Emission(), carRequest.getCo2Emission());
        assertEquals(car.getNoOfDoors(), carRequest.getNoOfDoors());
        assertEquals(car.getGrossPrice(), carRequest.getGrossPrice());
        assertEquals(car.getNetPrice(), carRequest.getNetPrice());
    }

    @Test
    public void testUpdateCar() {
        CarRequest carRequest = CarData.getCarRequest();
        carRequest.setVersion("2024");
        Car car = CarData.getCarData();
        Car updatedCar = CarData.getCarData();
        updatedCar.setVersion("2024");
        when(carRepository.findById(1L)).thenReturn(java.util.Optional.of(car));
        when(carRepository.save(any(Car.class))).thenReturn(updatedCar);
        carService.updateCar(1L, carRequest);

        assertEquals(updatedCar.getId(), 1L);
        assertEquals(updatedCar.getMake(), carRequest.getMake());
        assertEquals(updatedCar.getModel(), carRequest.getModel());
        assertEquals(updatedCar.getVersion(), carRequest.getVersion());
        assertEquals(updatedCar.getCo2Emission(), carRequest.getCo2Emission());
        assertEquals(updatedCar.getNoOfDoors(), carRequest.getNoOfDoors());
        assertEquals(updatedCar.getGrossPrice(), carRequest.getGrossPrice());
        assertEquals(updatedCar.getNetPrice(), carRequest.getNetPrice());
    }

    @Test
    public void testGetCarById() {
        CarRequest carRequest = CarData.getCarRequest();
        Car car = CarData.getCarData();

        when(carRepository.findById(1L)).thenReturn(java.util.Optional.of(car));
        carService.getCarById(1L);

        assertEquals(car.getId(), 1L);
        assertEquals(car.getMake(), carRequest.getMake());
        assertEquals(car.getModel(), carRequest.getModel());
        assertEquals(car.getVersion(), carRequest.getVersion());
        assertEquals(car.getCo2Emission(), carRequest.getCo2Emission());
        assertEquals(car.getNoOfDoors(), carRequest.getNoOfDoors());
        assertEquals(car.getGrossPrice(), carRequest.getGrossPrice());
        assertEquals(car.getNetPrice(), carRequest.getNetPrice());
    }

    @Test
    public void testGetAllCars() {
        List<Car> cars = CarData.getCars();
        when(carRepository.findAll()).thenReturn(cars);
        when(carRepository.findAll()).thenReturn(cars);
        List<Car> carList = carService.getAllCars();

        assertEquals(carList.size(), 1);
    }

    @Test
    public void testGetCarByIdNotFound() {
        CarNotFoundException exception = assertThrows(CarNotFoundException.class, () -> {
            carService.getCarById(3L);
        });
        assertEquals("Car not found", exception.getMessage());
    }

    @Test
    public void testUpdateCarNotFound() {
        CarNotFoundException exception = assertThrows(CarNotFoundException.class, () -> {
            carService.updateCar(3L, CarData.getCarRequest());
        });
        assertEquals("Car not found", exception.getMessage());
    }

    @Test
    public void calculateLeaseRate(){
        Car car = CarData.getCarData();

        LeaseRequest leaseRequest = CarData.getLeaseRequest();
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        LeaseRateResponse response = carService.getLeaseRate(leaseRequest);

        assertNotNull(response);
        assertEquals(response.getLeaseRate(), new BigDecimal("62.83"));
    }

    @Test
    public void testDeleteCarById() {
        carService.deleteCarById(1L);
        verify(carRepository, times(1)).deleteById(eq(1L));
    }

}
