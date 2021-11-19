package com.skarp.prio;

import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PrioApplication implements CommandLineRunner {

	@Autowired
	private ProductRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(PrioApplication.class, args);
	}

	@Override
	public void run(String... args) {
		repository.findById("2323");
	}
}
