package com.example.ticket_system.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "users")
@Getter
@Setter 
@NoArgsConstructor // JPAの実装に必須な「引数なしのデフォルトコンストラクタ」を自動生成
@AllArgsConstructor // すべてのフィールドを引数に持つコンストラクタを自動生成
@Builder // インスタンス生成を綺麗に書ける Builder パターンを有効化
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 50)
    private String roles;

    @Builder.Default // Builderパターンを使った時の初期値を true に固定する設定
    @Column(nullable = false)
    private boolean enabled = true;

    // 作成日時は外部から勝手に上書きされたくないため Setter を生成しない
    @Setter(lombok.AccessLevel.NONE) 
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 更新日時も自動管理にするため Setter を生成しない
    @Setter(lombok.AccessLevel.NONE)
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}