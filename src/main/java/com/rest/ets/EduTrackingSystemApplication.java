package com.rest.ets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@EnableJpaAuditing
@SpringBootApplication
public class EduTrackingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduTrackingSystemApplication.class, args);
	}

}
