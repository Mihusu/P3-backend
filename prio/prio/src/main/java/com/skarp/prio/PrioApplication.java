package com.skarp.prio;

import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductRepository;
import com.skarp.prio.repairs.Repair;
import com.skarp.prio.repairs.RepairRepository;
import com.skarp.prio.spareparts.NewSparePart;
import com.skarp.prio.spareparts.SparePart;
import com.skarp.prio.spareparts.SparePartRepository;
import com.skarp.prio.spareparts.UsedSparePart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
//		sparePartRepository.deleteAll();
//		repairRepository.deleteAll();

		Product product2 = new Product("Samsung", Category.smartphone, "Galaxy S3", "2018", "256 gb black", 3500, 2500);
		Product product = new Product("Apple", Category.iphone, "11 Pro", "", "128 gb white", 4000, 2000);
		Repair repair = new Repair(product);
		SparePart sparePart = new UsedSparePart(product, "A", "battery");

		productRepository.save(product2);
		productRepository.save(product);
		repairRepository.save(repair);
		sparePartRepository.save(sparePart);

	}
}
