package com.example.ticket_system.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticket_system.dto.booking.BookingRequest;
import com.example.ticket_system.model.Booking;
import com.example.ticket_system.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    // BookingServiceをコンストラクタインジェクション
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // チケット購入api
    @PostMapping
    public ResponseEntity<?> purchase(@RequestBody @Valid BookingRequest bookingRequest,
                                       @AuthenticationPrincipal String username) {
        try {
            // 認証情報から取得した安全な username と、リクエストの ticketId を使って購入処理を実行
            Booking booking = bookingService.purchaseTicket(username, bookingRequest.getTicketId());

            // 201と、作成された購入履歴を返却
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);

        } catch (RuntimeException e) {
            // 在庫切れやチケットが見つからないなどのエラーは 400 Bad Request とメッセージを返却
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
