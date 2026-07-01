package com.example.ticket_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ticket_system.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long>{
    
}
