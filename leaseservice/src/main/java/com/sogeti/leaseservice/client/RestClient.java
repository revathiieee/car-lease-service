package com.sogeti.leaseservice.client;

import com.sogeti.leaseservice.exception.TechnicalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * RestClient class.
 * This class is used to call customer-service and car-service.
 * @Author:revathi
 */
@Service
@Slf4j
public abstract class RestClient {

    protected abstract RestTemplate getRestTemplate();

    /**
     * Gets uri.
     * @return
     */
    protected abstract String getUri();

    /**
     * Gets service name.
     * @return
     */
    protected abstract String getServiceName();

    /**
     * Post t.
     * @param service
     * @param operation
     * @param request
     * @param headers
     * @param responseType
     * @return T
     * @param <R>
     * @param <T>
     */
    protected <R,T> T doPost(String service, String operation, R request, HttpHeaders headers, Class<T> responseType) {
        log.debug("Calling {} service, operation {}", service, operation);
        T value = null;
        try {
            HttpEntity<R> entity = new HttpEntity<R>(request, headers);
            ResponseEntity<T> response = getRestTemplate().postForEntity(getUri(), entity, responseType);
            if(response.getStatusCode().is2xxSuccessful()) {
                value = response.getBody();
            }  else {
                log.error("Error calling {} service, operation {} and statuscode {}", service, operation, response.getStatusCode());
                throw new TechnicalException("Error calling car service");
            }
        } catch (Exception e) {
            log.error("Error calling {} service, operation {}", service, operation, e.getMessage());
            throw new TechnicalException("Error calling car service");
        }
        return value;
    }

    /**
     * Do get t.
     * @param service
     * @param operation
     * @param id
     * @param headers
     * @param responseType
     * @param <T>
     * @return T
     */
    protected <T> T doGet(String service, String operation, Long id, HttpHeaders headers, Class<T> responseType) {
        log.info("Calling {} service, operation {}", service, operation);
        T value = null;
        try {
            ResponseEntity<T> response = getRestTemplate().exchange(getUri() + "/" + id, HttpMethod.GET, new HttpEntity<>(headers) , responseType);
            if(response.getStatusCode().is2xxSuccessful()) {
                value = response.getBody();
                log.debug("Response customer data: {}", value);
            }  else {
                log.error("Error calling {} service, operation {} and statuscode {}", service, operation, response.getStatusCode());
                throw new TechnicalException("Error calling customer service");
            }
        } catch (Exception e) {
            log.error("Error calling {} service, operation {}", service, operation, e.getMessage());
            throw new TechnicalException("Error calling customer service");
        }
        return value;
    }

}
