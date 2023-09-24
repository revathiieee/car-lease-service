package com.sogeti.carservice.controller;

import com.sogeti.carservice.model.Car;
import com.sogeti.carservice.model.request.CarRequest;
import com.sogeti.carservice.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import util.CarData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarControllerTest {

    @InjectMocks
    private CarController carController;

    @Mock
    private CarService carService;

    @Test
    public void testCreateCar() {

        CarRequest carRequest = CarData.getCarRequest();

        Car car = CarData.getCarData();
        when(carService.createCar(any(CarRequest.class))).thenReturn(car);
        carController.createCar(carRequest);

        assertEquals(car.getId(), 1L);
        assertEquals(car.getMake(), "Audi");
        assertEquals(car.getModel(), "A4");
        assertEquals(car.getVersion(), "2023");
        assertEquals(car.getCo2Emission(), "Emission");
        assertEquals(car.getNoOfDoors(), 4);
        assertEquals(car.getGrossPrice(), new BigDecimal(30000));
        assertEquals(car.getNetPrice(), new BigDecimal(30000));
    }

    @Test
    public void testGetAllCars() {

        Car car = CarData.getCarData();
        when(carService.getAllCars()).thenReturn(java.util.Arrays.asList(car));
        List<Car> carList = carController.getAllCars();
        assertEquals(carList.size(), 1);
    }

    @Test
    public void testGetCarById() {
        CarRequest carRequest = CarData.getCarRequest();

        Car car = CarData.getCarData();
        when(carService.getCarById(any(Long.class))).thenReturn(car);
        carController.getCarById(1L);

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
        Car car = CarData.getCarData();

        carController.updateCar(1L, carRequest);
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
    public void testDeleteCarById() {
        carController.deleteCarById(1L);
        verify(carService, times(1)).deleteCarById(eq(1L));
    }
}
