package com.sogeti.leaseservice.client;

import com.sogeti.leaseservice.exception.TechnicalException;
import com.sogeti.leaseservice.swagger.car.model.LeaseRateResponse;
import com.sogeti.leaseservice.swagger.car.model.LeaseRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * CarClient class.
 * This class is used to call car-service.
 * This class extends RestClient class.
 * @Author:revathi
 */
@Service
@Slf4j
public class CarClient extends RestClient {

    /**
     * The constant SERVICE_NAME.
     */
    private static final String SERVICE_NAME = "car-service";

    /**
     * The Rest template.
     */
    private final RestTemplate restTemplate;

    /**
     * The Car service uri.
     */
    @Value("${carservice.url}")
    private  String carServiceUri;

    /**
     * Instantiates a new Car client.
     * @param restTemplate
     */
    @Autowired
    public CarClient(@Qualifier("carRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * This method is used to get the lease rate from car-service
     * @param leaseRequest
     * @param token
     * @return LeaseRateResponse
     */
    public LeaseRateResponse getLeaseRate(com.sogeti.leaseservice.dto.LeaseRequest leaseRequest, String token) {
        LeaseRateResponse leaseRateResponse = null;
        HttpHeaders headers = createHeaders(token);
        LeaseRequest lreq = new LeaseRequest();
        lreq.setId(leaseRequest.getCarId());
        lreq.setMileage(leaseRequest.getMileage());
        lreq.setInterestRate(leaseRequest.getInterestRate());
        lreq.setDurationInMonths(leaseRequest.getDurationInMonths());
        try {
            leaseRateResponse = doPost(SERVICE_NAME, "LeaseRate", lreq, headers, LeaseRateResponse.class);
            log.debug("CarClient LeaseRateResponse: {}", leaseRateResponse);
        } catch (TechnicalException e) {
            log.error("Error while getting car details {}", e.getMessage());
            throw new TechnicalException("Error calling car service");
        }

        return leaseRateResponse;
    }

    /**
     * This method is used to create headers
     * @param token
     * @return HttpHeaders
     */
    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        return headers;
    }


    @Override
    protected RestTemplate getRestTemplate() {
        return this.restTemplate;
    }

    @Override
    protected String getUri() {
        return this.carServiceUri;
    }

    @Override
    protected String getServiceName() {
        return SERVICE_NAME;
    }
}
