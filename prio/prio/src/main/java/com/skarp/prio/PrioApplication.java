package com.skarp.prio;

import com.skarp.prio.products.ProductRepository;
import com.skarp.prio.repairs.RepairRepository;
import com.skarp.prio.spareparts.SparePartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;

@SpringBootApplication
public class PrioApplication implements CommandLineRunner {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private SparePartRepository sparePartRepository;

	@Autowired
	private RepairRepository repairRepository;

	public static void main(String[] args) {

		SpringApplication.run(PrioApplication.class, args);
	}

	@Override
	public void run(String... args) {

		//Product testProduct = new Product("697140000001","Lenovo", Category.LAPTOP,"E480","2016","17\"",4321,1234);
		//productRepository.save(testProduct);
		//productRepository.deleteAll(); // Please don't leave this active after testing
		//sparePartRepository.deleteAll();
		//repairRepository.deleteAll();
		System.out.println(Instant.now());

	}
}
