package com.sogeti.leaseservice.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * JwtUtil class is used to validate the password
 *
 * @Author revathi
 */
@Service
public class JwtUtil {

    private final PasswordEncoder encoder;

    public JwtUtil() {
        this.encoder = new BCryptPasswordEncoder(12);
    }

    public String hash(String password) {
        return encoder.encode(password);
    }

    public boolean matchHash(String password, String hash) {
        return encoder.matches(password, hash);
    }
}
