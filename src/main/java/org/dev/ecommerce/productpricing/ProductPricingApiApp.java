package org.dev.ecommerce.productpricing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"org.dev.ecommerce.productpricing"})
public class ProductPricingApiApp {

	public static void main(String[] args) {
		SpringApplication.run(ProductPricingApiApp.class, args);
	}

}
