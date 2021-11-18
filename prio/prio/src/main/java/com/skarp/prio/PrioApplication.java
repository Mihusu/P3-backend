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

		repository.deleteAll();
		repository.save(new Product("Hansi", Category.laptop, "TI", "2016", "8gb RAM", 3000, 500));
		repository.save(new Product("Macbook", Category.macbook, "M1", "2020", "16gb RAM", 8000, 9000));
		repository.save(new Product("Apple", Category.iphone, "13 pro", "2021", "512gb", 11500, 10500));


		System.out.println("All Products: ");
		for (Product product : repository.findAll()) {
			System.out.println(" - " + product.getName());
		}
	}
}
