package com.sogeti.leaseservice.util;

import com.sogeti.leaseservice.dto.AuthenticationDTO;
import com.sogeti.leaseservice.dto.AuthenticationResponse;
import com.sogeti.leaseservice.entity.User;

public interface TokenGenerator {
  AuthenticationResponse generateToken(User user);
}
