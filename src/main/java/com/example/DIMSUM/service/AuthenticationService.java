package com.example.DIMSUM.service;

import com.example.DIMSUM.request.AuthenticationRequest;
import com.example.DIMSUM.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
