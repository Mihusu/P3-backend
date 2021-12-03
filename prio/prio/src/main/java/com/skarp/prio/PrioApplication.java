package com.skarp.prio;

import com.skarp.prio.products.ProductRepository;
import com.skarp.prio.repairs.RepairRepository;
import com.skarp.prio.spareparts.SparePartRepository;
import com.skarp.prio.user.SHA3;
import com.skarp.prio.user.User;
import com.skarp.prio.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;

@SpringBootApplication
public class PrioApplication implements CommandLineRunner {

	// Todo: do the repositories need to be here?
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
	}
}
