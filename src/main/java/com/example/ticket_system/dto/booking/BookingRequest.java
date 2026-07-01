package com.example.ticket_system.dto.booking;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

// チケット購入リクエストを受け取るためのDTO

@Getter
@Setter
public class BookingRequest {
    @NotNull(message = "チケットIDは必須です")
    private Long ticketId;
}
