package com.element.challenge.hikebooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HikeBookingServiceApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(HikeBookingServiceApplication.class, args);
	}

}
