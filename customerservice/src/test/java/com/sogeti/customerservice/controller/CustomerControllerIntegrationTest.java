package com.sogeti.customerservice.controller;

import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sogeti.customerservice.model.request.CustomerRequest;
import com.sogeti.customerservice.util.CustomerData;
import java.util.ArrayList;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@SqlGroup({
    @Sql({"/schema.sql"}),
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:test-data.sql")
})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(OrderAnnotation.class)
public class CustomerControllerIntegrationTest {

  @Autowired
  private MockMvc mvc;

  @Autowired private JdbcTemplate jdbcTemplate;

  @Test
  @Order(1)
  public void testGetAllCustomers() throws Exception {
    mvc.perform(get("/customers/").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.*", isA(ArrayList.class)))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("john"))
        .andExpect(jsonPath("$[0].street").value("Amest"))
        .andExpect(jsonPath("$[0].houseNumber").value("103"))
        .andExpect(jsonPath("$[0].postalCode").value("2344HG"))
        .andExpect(jsonPath("$[0].city").value("DG"))
        .andExpect(jsonPath("$[0].email").value("john@test.com"))
        .andExpect(jsonPath("$[0].phoneNumber").value("123456"))

        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[1].name").value("doe"))
        .andExpect(jsonPath("$[1].street").value("bali"))
        .andExpect(jsonPath("$[1].houseNumber").value("104"))
        .andExpect(jsonPath("$[1].postalCode").value("2345HG"))
        .andExpect(jsonPath("$[1].city").value("DG"))
        .andExpect(jsonPath("$[1].email").value("doe@test.com"))
        .andExpect(jsonPath("$[1].phoneNumber").value("654321"));
  }

  @Test
  @Order(2)
  @Sql(statements = "DELETE FROM customer WHERE id='1'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void testGetCustomerById() throws Exception {
    mvc.perform(get("/customers/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("john"))
        .andExpect(jsonPath("$.street").value("Amest"))
        .andExpect(jsonPath("$.houseNumber").value("103"))
        .andExpect(jsonPath("$.postalCode").value("2344HG"))
        .andExpect(jsonPath("$.city").value("DG"))
        .andExpect(jsonPath("$.email").value("john@test.com"))
        .andExpect(jsonPath("$.phoneNumber").value("123456"));
  }

  @Test
  @Order(3)
  @Sql(statements = "DELETE FROM customer WHERE id='1'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  public void testCreateCustomer() throws Exception {
    jdbcTemplate.update("truncate table customer");
    CustomerRequest customerRequest = CustomerData.getCustomerRequest();
    customerRequest.setName("Peter");
    mvc.perform(MockMvcRequestBuilders
            .post("/customers/")
            .content(asJsonString(customerRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());
  }

  @Test
  @Order(4)
  public void testUpdateCustomer() throws Exception {
    CustomerRequest customerRequest = CustomerData.getCustomerRequest();
    customerRequest.setPhoneNumber("0612345678");
    customerRequest.setName("Peter");
    mvc.perform(MockMvcRequestBuilders
            .put("/customers/1")
            .content(asJsonString(customerRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());
  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
