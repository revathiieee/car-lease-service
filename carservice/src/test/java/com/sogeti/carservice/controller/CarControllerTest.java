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
import org.springframework.http.ResponseEntity;
import com.sogeti.carservice.util.CarData;

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
        ResponseEntity<Car> carRes = carController.createCar(carRequest);
        assertEquals(carRes.getStatusCodeValue(), 201);
        assertEquals(carRes.getBody().getId(), 1L);
        assertEquals(carRes.getBody().getMake(), "Audi");
        assertEquals(carRes.getBody().getModel(), "A4");
        assertEquals(carRes.getBody().getVersion(), "2023");
        assertEquals(carRes.getBody().getCo2Emission(), "Emission");
        assertEquals(carRes.getBody().getNoOfDoors(), 4);
        assertEquals(carRes.getBody().getGrossPrice(), new BigDecimal(30000));
        assertEquals(carRes.getBody().getNetPrice(), new BigDecimal(30000));
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
        ResponseEntity<Car> carRes = carController.getCarById(1L);
        assertEquals(carRes.getStatusCodeValue(), 200);
        assertEquals(carRes.getBody().getId(), 1L);
        assertEquals(carRes.getBody().getMake(), carRequest.getMake());
        assertEquals(carRes.getBody().getModel(), carRequest.getModel());
        assertEquals(carRes.getBody().getVersion(), carRequest.getVersion());
        assertEquals(carRes.getBody().getCo2Emission(), carRequest.getCo2Emission());
        assertEquals(carRes.getBody().getNoOfDoors(), carRequest.getNoOfDoors());
        assertEquals(carRes.getBody().getGrossPrice(), carRequest.getGrossPrice());
        assertEquals(carRes.getBody().getNetPrice(), carRequest.getNetPrice());
    }

    @Test
    public void testUpdateCar() {
        CarRequest carRequest = CarData.getCarRequest();
        carRequest.setMake("BMW3");
        Car car = CarData.getCarData();
        car.setMake("BMW3");
        when(carService.updateCar(eq(1L), any(CarRequest.class))).thenReturn(car);
        ResponseEntity<Car> carRes = carController.updateCar(1L, carRequest);
        assertEquals(carRes.getBody().getId(), 1L);
        assertEquals(carRes.getBody().getMake(), carRequest.getMake());
        assertEquals(carRes.getBody().getModel(), carRequest.getModel());
        assertEquals(carRes.getBody().getVersion(), carRequest.getVersion());
        assertEquals(carRes.getBody().getCo2Emission(), carRequest.getCo2Emission());
        assertEquals(carRes.getBody().getNoOfDoors(), carRequest.getNoOfDoors());
        assertEquals(carRes.getBody().getGrossPrice(), carRequest.getGrossPrice());
        assertEquals(carRes.getBody().getNetPrice(), carRequest.getNetPrice());
    }

    @Test
    public void testDeleteCarById() {
        carController.deleteCarById(1L);
        verify(carService, times(1)).deleteCarById(eq(1L));
    }
}
