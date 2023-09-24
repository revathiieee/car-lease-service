package com.sogeti.leaseservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.sogeti.leaseservice.dto.LeaseContractData;
import com.sogeti.leaseservice.dto.LeaseRequest;
import com.sogeti.leaseservice.service.LeaseServiceImpl;
import com.sogeti.leaseservice.swagger.car.model.Car;
import com.sogeti.leaseservice.swagger.car.model.LeaseData;
import com.sogeti.leaseservice.swagger.car.model.LeaseRateResponse;
import com.sogeti.leaseservice.swagger.customer.model.Customer;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
public class LeaseControllerTest {

  @InjectMocks
  private LeaseController leaseController;

  @Mock
  private LeaseServiceImpl leaseService;

  @Test
  public void testGetLeaseById() {
    LeaseRequest leaseRequest = new LeaseRequest();
    leaseRequest.setCustomerId(1L);

    Customer customer =  new Customer();
    customer.setId(1L);
    customer.setName("John");
    customer.setStreet("Main Street");
    customer.setHouseNumber("1");
    customer.setPostalCode("1234 AB");
    customer.setCity("Amsterdam");
    customer.setEmail("john@test.com");
    customer.setPhoneNumber("0612345678");

    LeaseRateResponse leaseRateResponse = new LeaseRateResponse();
    leaseRateResponse.setLeaseRate(new BigDecimal("345.7"));

    LeaseData leaseData = new LeaseData();
    leaseData.setMileage(10000);
    leaseData.setInterestRate(new BigDecimal("4.5"));
    leaseData.setDurationInMonths(60);

    Car car = new Car();
    car.setId(1L);
    car.setModel("BMW");
    car.setVersion("X5");
    car.setCo2Emission("PT");
    car.setMake("BMW");
    car.setNoOfDoors(4);
    car.setGrossPrice(new BigDecimal("50000"));
    car.setNetPrice(new BigDecimal("40000"));

    leaseData.setCar(car);
    leaseRateResponse.setLeaseData(leaseData);

    LeaseContractData lease = new LeaseContractData();
    lease.setCustomer(customer);
    lease.setLeaseRateResponse(leaseRateResponse);

    when(leaseService.getCustomerById(leaseRequest, "sometoken")).thenReturn(lease);
    leaseController.leaseDetails(leaseRequest, "sometoken");
    assertEquals(lease.getCustomer().getId(), 1L);
  }

}
