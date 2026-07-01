package com.example.ticket_system.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticket_system.dto.auth.JwtResponse;
import com.example.ticket_system.dto.auth.LoginRequest;
import com.example.ticket_system.dto.auth.SignupRequest;
import com.example.ticket_system.model.User;
import com.example.ticket_system.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // AuthServiceをコンストラクタインジェクション
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 新規会員登録
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        try {
            // リクエストdtoからエンティティに値を詰め替え（IDや日付、ロールは空の状態）
            User user = User.builder()
                    .username(signupRequest.getUsername())
                    .password(signupRequest.getPassword())
                    .email(signupRequest.getEmail())
                    .build();

            User registeredUser = authService.register(user);

            
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);

        } catch (RuntimeException e) {
            // ユーザー名重複などのエラーが起きた場合は400を返却
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // ログイン
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            // JWTトークンを取得
            String jwt = authService.login(loginRequest.getUsername(), loginRequest.getPassword());

            // 成功時：200 JWTトークンをラップしたJSONを返却
            return ResponseEntity.ok(new JwtResponse(jwt));

        } catch (RuntimeException e) {
            // ユーザー名・パスワード不一致、アカウント無効化などのエラーは401を返却
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
        }
    }
}