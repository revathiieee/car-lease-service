package com.sogeti.leaseservice.controller;

import com.sogeti.leaseservice.dto.LeaseContractData;
import com.sogeti.leaseservice.dto.LeaseRequest;
import com.sogeti.leaseservice.service.LeaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Lease controller.
 * This class is used to get the lease details
 * @Author revathi
 */
@RestController
@RequestMapping("/lease")
@Slf4j
public class LeaseController {

    /**
     * The Lease service.
     */
    private final LeaseServiceImpl leaseService;

    /**
     * Instantiates a new Lease controller.
     *
     * @param leaseService the lease service
     */
    public LeaseController(LeaseServiceImpl leaseService) {
        this.leaseService = leaseService;
    }

    /**
     * Lease details response entity.
     * This method is used to get the lease details
     * @param leaseRequest the lease request
     * @param token        the token
     * @return the response entity
     */
    @PostMapping("/")
    public ResponseEntity<LeaseContractData> leaseDetails(@RequestBody LeaseRequest leaseRequest, @RequestHeader(value = "Authorization", required = false) String token) {
        log.info("Calling leaseService.getCustomerById {}", leaseRequest.getCustomerId());
        return ResponseEntity.status(HttpStatus.OK).body(leaseService.getCustomerById(leaseRequest, token));
    }
}
