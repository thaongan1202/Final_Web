package com.utecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UtecommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UtecommerceApplication.class, args);
	}

}
