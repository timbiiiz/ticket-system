package com.example.ticket_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ticket_system.model.Booking;


public interface BookingRepository extends JpaRepository<Booking, Long> {
    
}
