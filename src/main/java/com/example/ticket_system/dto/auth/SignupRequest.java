package com.example.ticket_system.dto.auth;

import lombok.Getter;
import lombok.Setter;

// 会員登録リクエストを受け取るためのdto

@Getter
@Setter
public class SignupRequest {
    private String username;
    private String password;
    private String email;
    
}
