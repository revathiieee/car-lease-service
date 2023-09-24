package com.sogeti.carservice.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JWTValidator {

  public boolean isValid(String jwt) {
    try {
      var sJwt = SignedJWT.parse(jwt);
      JWSVerifier verifier = new MACVerifier("imasecretimasecretimasecretimasecret".getBytes(
          StandardCharsets.UTF_8));
      // verify validity
      if(sJwt.verify(verifier)){
        log.debug("JWT has valid signature");
        // verify expiration
        var expiration = sJwt.getJWTClaimsSet().getExpirationTime(); // if expiration is not present this will return null
        log.debug("JWT EXPIRING AT: " + expiration);
        if (expiration == null) {return false;}
        Date now = new Date();
        log.debug("NOW is: " + now);
        return now.before(expiration); // evaluate if the given expiration date is before now
      } else {
        log.debug("JWT has invalid signature");
        return false;
      }
    } catch (JOSEException | ParseException e) {
      return false;
    }
  }
}