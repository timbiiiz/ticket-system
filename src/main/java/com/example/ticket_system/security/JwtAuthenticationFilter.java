package com.example.ticket_system.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // Springの管理対象（Bean）にする
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // 以前作成したJWT関連のUtilityクラスをProviderとしてインジェクション
    private final JwtTokenProvider jwtTokenProvider;

    // コンストラクタインジェクション（Spring 4.3以降は@Autowired省略可能）
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        
        // Authorizationヘッダーから文字列を抽出
        String authHeader = request.getHeader("Authorization");
        String token;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // "Bearer " の7文字をスキップ
            try {
                // 有効性を検証してパース
                Claims claims = jwtTokenProvider.validateToken(token);
                username = claims.getSubject();
            } catch (JwtException e) {
                logger.error("JWTの検証に失敗しました: " + e.getMessage());
            }
        }

        // 有効なユーザー名が取れており、まだ認証されていない場合
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // DBは引かずに、トークンから取れたusernameを使って仮の認証オブジェクトを作成
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    username, null, Collections.emptyList()
            );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Spring Securityにリクエストokと伝える
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 次のフィルターへ処理を流す
        filterChain.doFilter(request, response);
    }
}