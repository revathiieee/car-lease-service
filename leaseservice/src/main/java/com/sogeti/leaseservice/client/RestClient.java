package com.sogeti.leaseservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public abstract class RestClient {

    protected abstract RestTemplate getRestTemplate();

    protected abstract String getUri();

    protected abstract String getServiceName();

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
            }
        } catch (Exception e) {
            log.error("Error calling {} service, operation {}", service, operation, e);
        }
        return value;
    }

    protected <T> T doGet(String service, String operation, Long id, HttpHeaders headers, Class<T> responseType) {
        log.info("Calling {} service, operation {}", service, operation);
        T value = null;
        try {
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            System.out.println(entity);
            ResponseEntity<T> response = getRestTemplate().exchange(getUri() + "/" + id, HttpMethod.GET, new HttpEntity<>(headers) , responseType);
            if(response.getStatusCode().is2xxSuccessful()) {
                value = response.getBody();
                log.debug("Response customer data: {}", value);
            }  else {
                log.error("Error calling {} service, operation {} and statuscode {}", service, operation, response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Error calling {} service, operation {}", service, operation, e);
        }
        return value;
    }

}
