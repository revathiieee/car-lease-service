package com.sogeti.carservice.util;

import com.sogeti.carservice.model.Car;
import com.sogeti.carservice.model.LeaseData;
import com.sogeti.carservice.model.request.CarRequest;
import com.sogeti.carservice.model.request.LeaseRequest;
import com.sogeti.carservice.model.response.LeaseRateResponse;
import java.math.BigDecimal;
import java.util.List;

public class CarData {

    public static Car getCarData() {
      Car car = new Car();
      car.setId(1L);
      car.setMake("Audi");
      car.setModel("A4");
      car.setVersion("2023");
      car.setCo2Emission("Emission");
      car.setNoOfDoors(4);
      car.setGrossPrice(new BigDecimal("30000"));
      car.setNetPrice(new BigDecimal("30000"));
      return car;
    }

    public static List<Car> getCars() {
      return List.of(getCarData());
    }

    public static CarRequest getCarRequest() {
      CarRequest carRequest = new CarRequest();
      carRequest.setMake("Audi");
      carRequest.setModel("A4");
      carRequest.setVersion("2023");
      carRequest.setCo2Emission("Emission");
      carRequest.setNoOfDoors(4);
      carRequest.setGrossPrice(new BigDecimal("30000"));
      carRequest.setNetPrice(new BigDecimal("30000"));
      return carRequest;
    }

    public static LeaseRequest getLeaseRequest() {
      LeaseRequest leaseRequest = new LeaseRequest();
      leaseRequest.setId(1L);
      leaseRequest.setDurationInMonths(12);
      leaseRequest.setInterestRate(new BigDecimal("2.5"));
      leaseRequest.setMileage(10000);
      return leaseRequest;
    }

    public static LeaseData getLeaseData() {
      LeaseData leaseData = new LeaseData();
      leaseData.setCar(getCarData());
      leaseData.setDurationInMonths(12);
      leaseData.setInterestRate(new BigDecimal("2.5"));
      leaseData.setMileage(10000);
      return leaseData;
    }

    public static LeaseRateResponse getLeaseRateResponse() {
      LeaseRateResponse leaseRateResponse = new LeaseRateResponse();
      leaseRateResponse.setLeaseRate(new BigDecimal("75.00"));
      leaseRateResponse.setLeaseData(getLeaseData());
      return leaseRateResponse;
    }

}
