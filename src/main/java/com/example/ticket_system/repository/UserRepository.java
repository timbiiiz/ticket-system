package com.example.ticket_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ticket_system.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // ユーザー名重複チェック(真偽だけを見るのでメモリ負荷を抑える)
    boolean existsByUsername(String username);
    // ユーザ名からユーザ情報の取得
    Optional<User> findByUsername(String username);
    
}
