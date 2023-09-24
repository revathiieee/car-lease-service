package com.sogeti.leaseservice.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sogeti.leaseservice.client.CustomerClient;
import com.sogeti.leaseservice.exception.TechnicalException;
import com.sogeti.leaseservice.swagger.customer.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class CustomerClientTest {

  private static final String CUSTOMER_RESPONSE= "{\n"
      + "  \"id\": 1,\n"
      + "  \"name\": \"Rithu\",\n"
      + "  \"street\": \"hhh\",\n"
      + "  \"houseNumber\": \"ddd\",\n"
      + "  \"postalCode\": \"dddd\",\n"
      + "  \"city\": \"cccc\",\n"
      + "  \"email\": \"ggg\",\n"
      + "  \"phoneNumber\": \"gggg\"\n"
      + "}";

  @Mock
  RestTemplate restTemplate;

  @Mock
  private ResponseEntity<Customer> customerResponseEntity;
  @InjectMocks
  private CustomerClient customerClient;

  @BeforeEach
  void setUp() {
    customerClient = new CustomerClient(restTemplate);
  }

  @Test
  void shouldValidate()
      throws JsonProcessingException {
    when(customerResponseEntity.getStatusCode()).thenReturn(OK);
    ObjectMapper mapper = new ObjectMapper();
    Customer customer = mapper.readValue(CUSTOMER_RESPONSE, Customer.class);
    when(customerResponseEntity.getBody()).thenReturn(customer);
    when(restTemplate.exchange(Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.eq(Customer.class)))
        .thenReturn(customerResponseEntity);
    Customer customerRes = customerClient.getCustomerById(1L, "token");
    assertNotNull(customerRes);
  }

  @Test
  void shouldValidateException () {
    when(customerResponseEntity.getStatusCode()).thenReturn(INTERNAL_SERVER_ERROR);
    when(restTemplate.exchange(Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.eq(Customer.class)))
        .thenReturn(customerResponseEntity);

    TechnicalException exception = assertThrows(TechnicalException.class, () -> {
      customerClient.getCustomerById(1L, "token");
    });
    assertEquals("Error calling customer service", exception.getMessage());
  }

}
