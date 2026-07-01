package com.example.ticket_system.security;

import java.util.Date;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenProvider {

    // 環境変数からシークレットキーと有効期限を取得
    // 環境変数名: JWT_SECRET, JWT_EXPIRATION_TIME
    private final String SECRET = System.getenv("JWT_SECRET");
    private final String EXPIRATION_STR = System.getenv("JWT_EXPIRATION_TIME");

    private SecretKey SECRET_KEY;
    private long EXPIRATION_TIME;

    // 環境変数を初期化・検証
    @PostConstruct 
    public void init() {
         if (SECRET == null || SECRET.trim().isEmpty()) {
            throw new IllegalStateException("環境変数 'JWT_SECRET' が設定されていません。");
        }
        if (SECRET.getBytes().length < 32) {
            throw new IllegalStateException("環境変数 'JWT_SECRET' は32バイト（256ビット）以上である必要があります。");
        }
        if (EXPIRATION_STR == null || EXPIRATION_STR.trim().isEmpty()) {
            throw new IllegalStateException("環境変数 'JWT_EXPIRATION_TIME' が設定されていません。");
        }

        try {
            SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
            EXPIRATION_TIME = Long.parseLong(EXPIRATION_STR);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("環境変数 'JWT_EXPIRATION_TIME' は有効な数値（ミリ秒）である必要があります。", e);
        }
    }
    
    // JWTトークン生成
    public String generateToken(String username, Set<String> roles) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(SECRET_KEY)
                .compact();
    }

    // JWTトークンを検証し、内容をパースして機嫌が切れてないか確認
    public Claims validateToken(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * JWTトークンからユーザー名を取得
     *
     * @param token フロントエンドから送られてきたJWTトークン文字列
     * @return ユーザー名（トークンが無効な場合はnull）
     */
    public String getUsernameFromToken(String token) {
        try {
            Claims claims = validateToken(token);
            return claims.getSubject();
        } catch (JwtException e) {
            return null;
        }
    }
}
