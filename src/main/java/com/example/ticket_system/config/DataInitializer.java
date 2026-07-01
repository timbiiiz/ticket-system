package com.example.ticket_system.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.ticket_system.model.Ticket;
import com.example.ticket_system.repository.TicketRepository;

/* H2テストデータ挿入 */

@Configuration
public class DataInitializer {

    @Bean
    @SuppressWarnings("unused")
    CommandLineRunner initDatabase(TicketRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                // 大人1日パス
                Ticket adultTicket = new Ticket();
                adultTicket.setName("大人1日パス");
                adultTicket.setPrice(new BigDecimal("8500"));
                adultTicket.setStock(10);
                repository.save(adultTicket);

                // 子供1日パス
                Ticket childTicket = new Ticket();
                childTicket.setName("子供1日パス");
                childTicket.setPrice(new BigDecimal("4500"));
                childTicket.setStock(10);                   
                repository.save(childTicket);
                
                System.out.println("H2データベースにテストデータを投入しました（大人・子供1日パス）");
            }
        };
    }
    
}
