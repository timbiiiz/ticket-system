package com.example.ticket_system.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ticket_system.model.Booking;
import com.example.ticket_system.model.Ticket;
import com.example.ticket_system.repository.BookingRepository;
import com.example.ticket_system.repository.TicketRepository;

@Service
public class BookingService {

    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;

    // 2つのリポジトリをコンストラクタインジェクション
    public BookingService(TicketRepository ticketRepository, BookingRepository bookingRepository) {
        this.ticketRepository = ticketRepository;
        this.bookingRepository = bookingRepository;
    }

    //チケット購入処理 在庫減少と履歴作成

    @Transactional
    public Booking purchaseTicket(String username, @NonNull Long ticketId) {
        
        // ticketId から対象のチケットを1件取得
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("指定されたIDのチケットが見つかりません。ID: " + ticketId));

        // チケットの stock（在庫）が0より大きいかチェック
        if (ticket.getStock() <= 0) {
            // 0以下なら「在庫切れエラー」を投げる
            throw new RuntimeException("申し訳ありません。このチケットは在庫切れです。");
        }

        // チケットの在庫をマイナス1して、更新
        ticket.setStock(ticket.getStock() - 1);
        ticketRepository.save(ticket);

        // Bookingエンティティを生成・保存
        Booking booking = Booking.builder()
                .username(username)
                .ticketId(ticketId)
                .build();

        // 購入履歴をDBに保存 保存完了後のオブジェクトを返却
        return bookingRepository.save(booking);
    }
}