package com.eventhub.outboxpublisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OutboxPublisherApplication {

	public static void main(String[] args) {
		SpringApplication.run(OutboxPublisherApplication.class, args);
	}

}
