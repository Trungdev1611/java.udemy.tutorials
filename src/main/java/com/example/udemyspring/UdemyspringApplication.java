package com.example.udemyspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class UdemyspringApplication {

	public static void main(String[] args) {
		SpringApplication.run(UdemyspringApplication.class, args);
	}

}
