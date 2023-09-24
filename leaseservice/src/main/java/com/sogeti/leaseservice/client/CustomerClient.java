package com.sogeti.leaseservice.client;

import com.sogeti.leaseservice.exception.TechnicalException;
import com.sogeti.leaseservice.swagger.customer.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * CustomerClient class.
 * This class is used to call customer-service.
 * This class extends RestClient class.
 * @Author:revathi
 */
@Service
@Slf4j
public class CustomerClient extends RestClient {

    /**
     * The constant SERVICE_NAME.
     */
    private static final String SERVICE_NAME = "customer-service";

    /**
     * The Rest template.
     */
    private final RestTemplate restTemplate;

    /**
     * The Customer service uri.
     */
    @Value("${customerservice.url}")
    private  String customerServiceUri;

    /**
     * Instantiates a new Customer client.
     * @param restTemplate
     */
    @Autowired
    public CustomerClient(@Qualifier("customerRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * This method is used to get the customer details from customer-service
     * @param id
     * @param token
     * @return Customer
     */
    public Customer getCustomerById(Long id, String token) {
        Customer customer = null;
        HttpHeaders headers = createHeaders(token);
        try {
            customer = doGet(SERVICE_NAME, "getCustomerById", id, headers, Customer.class);
            log.debug("CustomerClient Customer: {}", customer);
        } catch (TechnicalException e) {
            log.error("Error while getting customer details {}", e.getMessage());
            throw new TechnicalException("Error calling customer service");
        }
        return customer;
    }

    /**
     * This method is used to create headers
     * @param token
     * @return HttpHeaders
     */
    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }

    /**
     * This method is used to get the rest template
     * @return RestTemplate
     */
    @Override
    protected RestTemplate getRestTemplate() {
        return this.restTemplate;
    }

    /**
     * This method is used to get the uri
     * @return String
     */
    @Override
    protected String getUri() {
        return this.customerServiceUri;
    }

    /**
     * This method is used to get the service name
     * @return String
     */
    @Override
    protected String getServiceName() {
        return SERVICE_NAME;
    }
}
