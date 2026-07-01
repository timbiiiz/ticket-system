package com.example.ticket_system.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 購入履歴のエンティティ

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 購入したユーザー名
    @Column(nullable = false, length = 50)
    private String username;

    // 購入されたチケットid
    @Column(name = "ticket_id", nullable = false)
    private Long ticketId;

    // 購入日時
    @Setter(lombok.AccessLevel.NONE)
    @Column(name = "purchase_at", nullable = false, updatable = false)
    private LocalDateTime purchaseAt;

   
    // DBに購入履歴が挿入される直前にその瞬間の時刻をセット
    @PrePersist
    protected void onPurchase() {
        this.purchaseAt = LocalDateTime.now();
    }
}