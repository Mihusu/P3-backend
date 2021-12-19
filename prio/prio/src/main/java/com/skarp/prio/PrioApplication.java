package com.skarp.prio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PrioApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(PrioApplication.class, args);
	}

	@Override
	public void run(String... args) {

	}
}
