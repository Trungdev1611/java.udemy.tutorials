package com.example.udemyspring;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;;

@SpringBootApplication
// add info swagger documentation
// http://localhost:8080/swagger-ui/index.html
@OpenAPIDefinition(info = @Info(title = "Springboot rest API documentation Udemy", description = "Sử dụng annotation OpenAPIDefinition trong file main app để viết document", version = "v1.0", contact = @Contact(name = "Trung", email = "trungdev1611@gmail.com", url = "http://localhost:8080"), license = @License(name = "Apache 2.0", url = "http://localhost:8080/license")))

public class UdemyspringApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(UdemyspringApplication.class, args);
	}

}
