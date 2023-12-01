package com.eventorganizer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EventOrganizerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventOrganizerAppApplication.class, args);
	}

}
