package com.sogeti.leaseservice.controller;

import com.sogeti.leaseservice.dto.AuthenticationDTO;
import com.sogeti.leaseservice.dto.AuthenticationResponse;
import com.sogeti.leaseservice.service.UserDetailsServiceImpl;
import com.sogeti.leaseservice.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * The type Authentication controller.
 * This class is used to authenticate the user
 * @Author revathi
 */
@RestController
@RequestMapping("/api")
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
    private AuthenticationManager authenticationManager;

    /**
     * The User details service.
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * Create authentication token authentication response.
     * This method is used to authenticate the user
     * @param authenticationDTO the authentication dto
     * @param response          the response
     * @return the authentication response
     * @throws BadCredentialsException    the bad credentials exception
     * @throws DisabledException          the disabled exception
     * @throws UsernameNotFoundException the username not found exception
     * @throws IOException                the io exception
     */
    @PostMapping("/authenticate")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationDTO authenticationDTO, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(), authenticationDTO.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password!");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not activated");
            return null;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDTO.getEmail());

        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return new AuthenticationResponse(jwt);

    }

}
