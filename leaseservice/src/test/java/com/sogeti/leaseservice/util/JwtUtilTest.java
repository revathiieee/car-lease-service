package com.sogeti.leaseservice.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class JwtUtilTest {

  @Test
  public void testGenerateToken() {
    var hash = "$2a$10$h9DjsxushmrJ/xcE2ygSJOkgoBOUKgnqxRDEL1r85/9HxgzF8qkXW";
    var password = "adminpwd";
    var jwtUtil= new JwtUtil();
    assertTrue(jwtUtil.matchHash(password, hash));
  }

  @Test
  public void testGenerateTokenFalse() {
    var hash = "$2a$10$h9DjsxushmrJ/xcE2ygSJOkgoBOUKgnqxRDEL1r85/9HxgzF8qkXWHHH";
    var password = "adminpwd";
    var jwtUtil= new JwtUtil();
    assertFalse(jwtUtil.matchHash(password, hash));
  }
}
