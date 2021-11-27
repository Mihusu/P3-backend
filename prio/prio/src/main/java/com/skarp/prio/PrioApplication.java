package com.skarp.prio;

import com.skarp.prio.products.Category;
import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductRepository;
import com.skarp.prio.repairs.Repair;
import com.skarp.prio.repairs.RepairRepository;
import com.skarp.prio.spareparts.Enums.Grade;
import com.skarp.prio.spareparts.SparePart;
import com.skarp.prio.spareparts.SparePartRepository;
import com.skarp.prio.spareparts.UsedSparePart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

		productRepository.deleteAll();
		//sparePartRepository.deleteAll();
		//repairRepository.deleteAll();

	}
}
