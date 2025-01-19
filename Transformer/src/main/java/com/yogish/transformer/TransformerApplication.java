package com.yogish.transformer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {
		"com.yogish"

})
public class TransformerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransformerApplication.class, args);
	}

}
