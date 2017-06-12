package org.dev.ecommerce.productpricing;

import org.dev.ecommerce.productpricing.model.ProductPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class ProductPricingApiAppTestClient {

	public static final Logger logger = LoggerFactory.getLogger(ProductPricingApiAppTestClient.class);

	public static final String REST_SERVICE_URI = "http://localhost:8082/ProductPricing";

	/* GET */
	private static void getProductPrice() {
		try {
			logger.debug("\n\n");
			logger.debug("Testing get Product Price API----------");
			RestTemplate restTemplate = new RestTemplate();
			ProductPrice product = restTemplate.getForObject(REST_SERVICE_URI + "/prices/p1234567890",
					ProductPrice.class);
			logger.debug(product.toString());
		} catch (Exception ex) {

		}
	}

	/* POST */
	private static void createProductPrice() {
		try {
			logger.debug("\n\n");
			logger.debug("Testing create Product Price API----------");
			RestTemplate restTemplate = new RestTemplate();
			ProductPrice productPrice = null;
			productPrice = new ProductPrice("p1234567890", "9000", "Nike Shoe Price");
			restTemplate.postForLocation(REST_SERVICE_URI + "/prices/", productPrice, ProductPrice.class);
		} catch (Exception ex) {

		}
	}

	/* PUT */
	private static void updateProductPrice() {
		try {
			logger.debug("\n\n");
			logger.debug("Testing update Product Price API----------");
			RestTemplate restTemplate = new RestTemplate();
			ProductPrice productPrice = new ProductPrice("p1234567890", "7000", "Rebock Shoe Price");
			restTemplate.put(REST_SERVICE_URI + "/prices/p1234567890", productPrice);
			logger.debug(productPrice.toString());
		} catch (Exception ex) {

		}
	}

	/* DELETE */
	private static void deleteProductPrice() {
		try {
			logger.debug("\n\n");
			logger.debug("Testing delete Product API----------");
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.delete(REST_SERVICE_URI + "/prices/p1234567890");
		} catch (Exception ex) {

		}
	}

	public static void main(String args[]) {
		getProductPrice();
		createProductPrice();
		getProductPrice();
		updateProductPrice();
		getProductPrice();
		deleteProductPrice();
		getProductPrice();
	}
	
}