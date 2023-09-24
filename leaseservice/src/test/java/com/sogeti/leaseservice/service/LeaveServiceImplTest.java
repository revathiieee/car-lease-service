package com.sogeti.leaseservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.sogeti.leaseservice.client.CarClient;
import com.sogeti.leaseservice.client.CustomerClient;
import com.sogeti.leaseservice.dto.LeaseContractData;
import com.sogeti.leaseservice.dto.LeaseRequest;
import com.sogeti.leaseservice.exception.TechnicalException;
import com.sogeti.leaseservice.swagger.car.model.Car;
import com.sogeti.leaseservice.swagger.car.model.LeaseData;
import com.sogeti.leaseservice.swagger.car.model.LeaseRateResponse;
import com.sogeti.leaseservice.swagger.customer.model.Customer;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class LeaveServiceImplTest {

  @InjectMocks
  private LeaseServiceImpl leaseServiceImpl;

  @Mock
  private CarClient carClient;

  @Mock
  private CustomerClient customerClient;

  @BeforeEach
  public void setUp() {
    leaseServiceImpl = new LeaseServiceImpl(customerClient, carClient);
    ReflectionTestUtils.setField(carClient, "carServiceUri", "http://localhost:8081/car/leaseRate");
    ReflectionTestUtils.setField(customerClient, "customerServiceUri", "http://localhost:8081/customers");
  }

  @Test
  public void testGetCustomerById() {
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

    LeaseRequest leaseRequest = new LeaseRequest();
    leaseRequest.setCustomerId(1L);
    leaseRequest.setCarId(1L);

    when(customerClient.getCustomerById(Mockito.anyLong(), Mockito.anyString())).thenReturn(customer);
    when(carClient.getLeaseRate(Mockito.any(), Mockito.anyString())).thenReturn(leaseRateResponse);
    LeaseContractData leaseContractData = leaseServiceImpl.getCustomerById(leaseRequest, "sometoken");
    assert leaseContractData != null;
    assertEquals(leaseContractData.getCustomer().getName(), "John");
    assertEquals(leaseContractData.getLeaseRateResponse().getLeaseRate(), new BigDecimal("345.7"));
  }

  @Test
  void testGetCustomerByIdException () {;
    LeaseRequest leaseRequest = new LeaseRequest();
    leaseRequest.setCustomerId(1L);
    leaseRequest.setCarId(1L);
    when(customerClient.getCustomerById(Mockito.anyLong(), Mockito.anyString())).thenThrow(new TechnicalException("Error while getting customer data"));
    TechnicalException exception = assertThrows(TechnicalException.class, () -> {
      leaseServiceImpl.getCustomerById(leaseRequest, "sometoken");
    });
    assertEquals("Error while getting lease contract data", exception.getMessage());
  }

  @Test
  void testGetCustomerByIdException2 () {;
    LeaseRequest leaseRequest = new LeaseRequest();
    leaseRequest.setCustomerId(1L);
    leaseRequest.setCarId(1L);
    when(customerClient.getCustomerById(Mockito.anyLong(), Mockito.anyString())).thenReturn(new Customer());
    when(carClient.getLeaseRate(Mockito.any(), Mockito.anyString())).thenThrow(new TechnicalException("Error while getting car data"));
    TechnicalException exception = assertThrows(TechnicalException.class, () -> {
      leaseServiceImpl.getCustomerById(leaseRequest, "sometoken");
    });
    assertEquals("Error while getting lease contract data", exception.getMessage());
  }

}
