package com.example.ticket_system.dto.auth;

import lombok.Getter;
import lombok.Setter;

// ログインリクエストを受け取るためのdto

@Getter
@Setter
public class LoginRequest {
    
    private String username;
    private String password;
}
