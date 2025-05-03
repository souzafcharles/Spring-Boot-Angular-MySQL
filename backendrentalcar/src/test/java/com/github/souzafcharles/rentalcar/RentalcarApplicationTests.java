package com.github.souzafcharles.rentalcar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class RentalcarApplicationTests {

	@Test
	@DisplayName("Should load Spring Boot application context without errors")
	void contextLoads() {
		assertDoesNotThrow(() -> SpringApplication.run(RentalcarApplication.class));
	}

}