package com.example.ticket_system.service;

import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ticket_system.model.User;
import com.example.ticket_system.repository.UserRepository;
import com.example.ticket_system.security.JwtTokenProvider;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;

    }

    @Transactional
    public User register(User user) {
        // 重複チェック
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("このユーザー名はすでに使用されています。");
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setRoles("ROLE_USER");

        return userRepository.save(user);
    }

    public String login(String username, String password) {
        // データベースからユーザーを検索
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("ユーザー名またはパスワードが正しくありません。"));
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("ユーザー名またはパスワードが正しくありません。");
        }

        if (!user.isEnabled()) {
            throw new RuntimeException("このアカウントは無効化されています。");
        }

        // 認証成功：JWTトークンを生成して返却
        return jwtTokenProvider.generateToken(user.getUsername(), Collections.singleton(user.getRoles()));
    }
}
