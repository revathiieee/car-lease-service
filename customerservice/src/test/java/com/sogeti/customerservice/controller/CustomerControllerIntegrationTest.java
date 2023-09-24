package com.sogeti.customerservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sogeti.customerservice.model.Customer;
import com.sogeti.customerservice.model.request.CustomerRequest;
import com.sogeti.customerservice.repository.CustomerRepository;
import com.sogeti.customerservice.service.CustomerService;
import com.sogeti.customerservice.util.CustomerData;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private CustomerRepository customerRepository;

  private static HttpHeaders headers;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeAll
  public static void init() {
    headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth("some-token");
  }
  private String createURLWithPort() {
    return "http://localhost:" + port + "/customers";
  }

  @Test
  @Sql(statements = "INSERT INTO customer(id, name, street, house_number, postal_code, city, email, phone_number) VALUES (1, 'john', 'Amest', '103', '2344HG', 'DG', 'john@test.com', '123456')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(statements = "DELETE FROM customer WHERE id='1'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void testCustomersList() {
    HttpEntity<String> entity = new HttpEntity<>(null, headers);
    ResponseEntity<Customer> response =  restTemplate.exchange(
        (createURLWithPort() + "/1"), HttpMethod.GET, entity, Customer.class);
    Customer customer = response.getBody();
    assert customer != null;
    assertEquals(response.getStatusCodeValue(), 200);
    String expected = "{\"id\":1,\"name\":\"john\",\"street\":\"Amest\",\"houseNumber\":\"103\",\"postalCode\":\"2344HG\",\"city\":\"DG\",\"email\":\"john@test.com\",\"phoneNumber\":\"123456\"}";
    assertEquals(customer, customerService.getCustomerById(1L));
    assertEquals(customer.getName(), customerService.getCustomerById(1L).getName());
    assertEquals(customer.getHouseNumber(), customerService.getCustomerById(1L).getHouseNumber());
    assertEquals(customer.getStreet(), customerService.getCustomerById(1L).getStreet());
    assertEquals(customer, customerRepository.findById(1L).orElse(null));
  }


//  @Test
//  @Sql(statements = "DELETE FROM customer WHERE id='1'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//  public void testCreateCustomer() throws JsonProcessingException {
//    CustomerRequest customerRequest = CustomerData.getCustomerRequest();
//    Customer customer = CustomerData.getCustomer();
//    HttpEntity<CustomerRequest> entity = new HttpEntity<>(customerRequest, headers);
//    ResponseEntity<Customer> response = restTemplate.postForEntity(createURLWithPort(), entity, Customer.class);
//    assertEquals(response.getStatusCodeValue(), 201);
//    Customer customerResp = Objects.requireNonNull(response.getBody());
//    assertEquals(customerResp.getName(), "John Doe");
//    assertEquals(customerResp.getStreet(), customerRepository.save(customer).getStreet());
//  }

}
