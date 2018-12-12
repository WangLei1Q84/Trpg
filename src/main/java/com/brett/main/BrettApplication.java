package com.brett.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages="com.brett")
@SpringBootApplication
public class BrettApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrettApplication.class, args);
	}
}
