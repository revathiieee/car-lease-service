package com.sogeti.leaseservice.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.sogeti.leaseservice.dto.AuthenticationResponse;
import com.sogeti.leaseservice.entity.User;
import com.sogeti.leaseservice.util.TokenGenerator;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JWTTokenGeneratorImpl implements TokenGenerator {

  @Override
  public AuthenticationResponse generateToken(User user) {
    try {
      JWSSigner signer = new MACSigner("imasecretimasecretimasecretimasecret".getBytes(StandardCharsets.UTF_8));

      JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
          .subject(user.getEmail())
          .issuer("https://carlease.com")
          .expirationTime(new Date(new Date().getTime() + 3600 * 1000)) // expiration in seconds to millis
          .build();
      SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
      signedJWT.sign(signer);
      String jwt = signedJWT.serialize(); // does not base64 encoded signature
      return new AuthenticationResponse(jwt);
    } catch (JOSEException e) {
      e.printStackTrace();
    }
    throw new RuntimeException("Token creation failed"); // should never get here
  }
}
