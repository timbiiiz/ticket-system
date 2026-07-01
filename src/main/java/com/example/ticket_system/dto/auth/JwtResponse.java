package com.example.ticket_system.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

// ログイン成功時にjwtトークンをラップして返すためのレスポンスdto

@Getter
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private final String type = "Bearer";
    
}
