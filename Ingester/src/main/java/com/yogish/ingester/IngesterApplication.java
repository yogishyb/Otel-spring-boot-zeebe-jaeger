package com.yogish.ingester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration
public class IngesterApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngesterApplication.class, args);
	}

}
