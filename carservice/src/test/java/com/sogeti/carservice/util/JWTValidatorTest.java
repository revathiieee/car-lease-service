package com.sogeti.carservice.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JWTValidatorTest {

  private String secret;
  private String validJWT;
  @BeforeEach
  private void init() throws JOSEException {
    secret = "imasecretimasecretimasecretimasecret";
    JWSSigner signer = new MACSigner(secret.getBytes(StandardCharsets.UTF_8));

    JWTClaimsSet claimsSet =
        new JWTClaimsSet.Builder()
            .expirationTime(
                new Date(new Date().getTime() + 60 * 1000)) // expiration in seconds to millis
            .build();
    SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
    signedJWT.sign(signer);
    validJWT = signedJWT.serialize();
  }

  @Test
  void isValidTrue() {
    var jwtValidator = new JWTValidator();
    assertTrue(jwtValidator.isValid(validJWT));
  }

  @Test
  void isValidFalse() {
    String jwt =
        "dyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.HqXboI3jgtnSoM4cT_WaWDTgQ83wMaJVL2X0pdUEvaU";
    var jwtValidator = new JWTValidator();
    assertFalse(jwtValidator.isValid(jwt));
  }

  @Test
  void isValidExpired() throws JOSEException {
    var jwtValidator = new JWTValidator();
    JWSSigner signer = new MACSigner(secret);

    JWTClaimsSet claimsSet =
        new JWTClaimsSet.Builder()
            .expirationTime(
                new Date(
                    ZonedDateTime.now()
                        .minusHours(4)
                        .toEpochSecond())) // setting expiration to before now
            .build();
    SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
    signedJWT.sign(signer);
    String expiredJWT = signedJWT.serialize();

    assertFalse(jwtValidator.isValid(expiredJWT));
  }


}
