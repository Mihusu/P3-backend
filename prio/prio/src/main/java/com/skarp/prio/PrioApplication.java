package com.skarp.prio;

import com.skarp.prio.products.ProductRepository;
import com.skarp.prio.repairs.RepairRepository;
import com.skarp.prio.spareparts.SparePartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
public class
PrioApplication implements CommandLineRunner {

	// Todo: do the repositories need to be here, I commented them out and everything still works?
	/*
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private SparePartRepository sparePartRepository;

	@Autowired
	private RepairRepository repairRepository;

	@Autowired
	private UserRepository userRepository;

 */
	public static void main(String[] args) {

		SpringApplication.run(PrioApplication.class, args);
	}

	@Override
	public void run(String... args) {

		System.out.println("-------------------------------------------------------------------------------------");
		System.out.println("Which format looks nicer?");
		System.out.println("Instant: " + Instant.now() + " LocalDate: " + LocalDate.now() + " Date: " + new Date());
		System.out.println("-------------------------------------------------------------------------------------");
	}
}
