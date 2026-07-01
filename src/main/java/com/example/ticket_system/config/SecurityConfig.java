package com.example.ticket_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.ticket_system.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // JwtAuthenticationFilterをコンストラクタインジェクション
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // パスワード暗号化
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF保護を無効化（ステートレスなJWT認証のため）
            .csrf(csrf -> csrf.disable())

            // セッション管理をSTATELESS（不保持）に設定
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // アクセス権限の制御（URLごとの認可設定）
            .authorizeHttpRequests(auth -> auth
                // 認証不要な公開エンドポイントの指定
                .requestMatchers("/api/auth/**").permitAll() // 会員登録とログインは全員許可
                .requestMatchers(HttpMethod.GET, "/api/tickets").permitAll() // チケット一覧取得（GETのみ公開）
                
                // 上記以外（チケット購入 /api/tickets/purchase など）はすべて認証必須
                .anyRequest().authenticated()
            )

            // JwtAuthenticationFilterをUsernamePasswordAuthenticationFilterの前に挿入
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
