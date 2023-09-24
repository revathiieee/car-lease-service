package com.sogeti.leaseservice.controller;

import com.sogeti.leaseservice.dto.AuthenticationDTO;
import com.sogeti.leaseservice.dto.AuthenticationResponse;
import com.sogeti.leaseservice.repository.UserRepository;
import com.sogeti.leaseservice.util.JwtUtil;
import com.sogeti.leaseservice.util.TokenGenerator;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Authentication controller.
 * This class is used to authenticate the user
 * @Author revathi
 */
@RestController
@Slf4j
public class AuthenticationController {

    /**
     * The Jwt util.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * The Authentication manager.
     */
    @Autowired
    private TokenGenerator tokenGenerator;

    /**
     * The User details service.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Create authentication token response entity.
     * @param authenticationDTO
     * @return AuthenticationResponse
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationDTO authenticationDTO) {

        var user = userRepository.findFirstByEmail(authenticationDTO.getEmail());
        log.debug("User: {}", user);
        if (user != null && jwtUtil.matchHash(authenticationDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.OK).body(tokenGenerator.generateToken(user));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
