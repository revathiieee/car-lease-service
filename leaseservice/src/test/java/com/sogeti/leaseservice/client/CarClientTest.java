package com.sogeti.leaseservice.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sogeti.leaseservice.client.CarClient;
import com.sogeti.leaseservice.dto.LeaseRequest;
import com.sogeti.leaseservice.exception.TechnicalException;
import com.sogeti.leaseservice.swagger.car.model.LeaseRateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class CarClientTest {


  private static final String CAR_URI="http://localhost:8081/car/leaseRate";

  private static final String LEASE_RATE_RESPONSE = "{\n"
      + "  \"leaseData\": {\n"
      + "    \"mileage\": 10000,\n"
      + "    \"durationInMonths\": 60,\n"
      + "    \"interestRate\": 4.5,\n"
      + "    \"car\": {\n"
      + "      \"id\": 2,\n"
      + "      \"make\": \"Tesla\",\n"
      + "      \"model\": \"2013\",\n"
      + "      \"version\": \"v1\",\n"
      + "      \"co2Emission\": \"Battery\",\n"
      + "      \"noOfDoors\": 4,\n"
      + "      \"grossPrice\": 100000,\n"
      + "      \"netPrice\": 100000\n"
      + "    }\n"
      + "  },\n"
      + "  \"leaseRate\": 375.5\n"
      + "}";

  @Mock
  RestTemplate restTemplate;

  @Mock
  private ResponseEntity<LeaseRateResponse> carResponseEntity;

  private CarClient carClient;

  @BeforeEach
  void setUp() {
    carClient = new CarClient(restTemplate);
    ReflectionTestUtils.setField(carClient, "carServiceUri", "http://localhost:8081/car/leaseRate");
  }

  @Test
  void shouldValidate()
      throws JsonProcessingException {
    LeaseRequest leaseRequest = new LeaseRequest();
    when(carResponseEntity.getStatusCode()).thenReturn(OK);
    ObjectMapper mapper = new ObjectMapper();
    LeaseRateResponse leaseRateResponse = mapper.readValue(LEASE_RATE_RESPONSE, LeaseRateResponse.class);
    when(carResponseEntity.getBody()).thenReturn(leaseRateResponse);
    when(restTemplate.postForEntity(isA(String.class), isA(HttpEntity.class), eq(LeaseRateResponse.class)))
        .thenReturn(carResponseEntity);
    LeaseRateResponse leaseRateRes = carClient.getLeaseRate(leaseRequest, "token");
    assertNotNull(leaseRateRes);
  }

  @Test
  void shouldValidateException () {
    LeaseRequest leaseRequest = new LeaseRequest();
    when(carResponseEntity.getStatusCode()).thenReturn(INTERNAL_SERVER_ERROR);
    when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(), eq(LeaseRateResponse.class)))
        .thenReturn(carResponseEntity);

    TechnicalException exception = assertThrows(TechnicalException.class, () -> {
      carClient.getLeaseRate(leaseRequest, "token");
    });
    assertEquals("Error calling car service", exception.getMessage());
  }

}
