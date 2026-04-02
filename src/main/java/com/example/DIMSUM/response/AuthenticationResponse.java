package com.example.DIMSUM.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    private String token;
    private boolean authenticated;
}
