package com.eventhub.outboxpublisher;

import com.eventhub.outboxpublisher.service.OutboxPublisherService;
import jakarta.persistence.Enumerated;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OutboxPublisherApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutboxPublisherApplication.class, args);
    }

/*    @Bean
    CommandLineRunner testPublisher(OutboxPublisherService service) {
        return args -> service.publishPendingEvents();
    }*/
}
