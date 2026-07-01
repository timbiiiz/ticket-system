package com.example.ticket_system.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticket_system.model.Ticket;
import com.example.ticket_system.repository.TicketRepository;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
    
    // チケット一覧取得
    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        return ResponseEntity.ok(tickets); // 200 OK と一緒にリストを返却
    }

    // 新規チケット登録
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody @Valid @NonNull Ticket ticket) {
        // 送られてきたチケット情報をそのままDBに保存
        Ticket savedTicket = ticketRepository.save(ticket);
        // 201 Created ステータスと保存されたデータを返却
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTicket);
    }
    
    // チケット情報更新
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable @Valid @NonNull Long id, @RequestBody Ticket ticketDetails) {
        // 指定されたIDのチケットが実在するか確認
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("指定されたIDのチケットが見つかりません。ID: " + id));

        // 既存のデータに、画面から送られてきた新しい値をセット
        ticket.setName(ticketDetails.getName());
        ticket.setPrice(ticketDetails.getPrice());
        ticket.setStock(ticketDetails.getStock());

        //　上書き保存
        Ticket updatedTicket = ticketRepository.save(ticket);
        
        return ResponseEntity.ok(updatedTicket); // 200 OK
    }

    // チケット削除
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable @Valid @NonNull Long id) {
        // 指定されたIDのチケットが実在するか確認
        if (!ticketRepository.existsById(id)) {
            throw new RuntimeException("指定されたIDのチケットが見つかりません。ID: " + id);
        }

        // 削除実行
        ticketRepository.deleteById(id);
        
        // 204 No Content（処理は成功したが、返す中身は空っぽ）を返却
        return ResponseEntity.noContent().build();
    }
    
}
