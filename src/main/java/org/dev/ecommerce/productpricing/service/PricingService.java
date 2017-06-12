package org.dev.ecommerce.productpricing.service;

import java.io.IOException;

import org.dev.ecommerce.productpricing.model.ProductPrice;

public interface PricingService {

	ProductPrice save(ProductPrice detail);

	ProductPrice findOne(String id);

	void delete(String id);

	ProductPrice update(String id, ProductPrice product) throws IOException;

}
