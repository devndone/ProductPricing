package org.dev.ecommerce.productpricing.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.dev.ecommerce.productpricing.model.ProductPrice;
import org.dev.ecommerce.productpricing.service.PricingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(basePath = "/prices", value = "Prices", description = "Operations with ProductPrice Prices", produces = "application/json")
@RestController
@RequestMapping(value = "/prices", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductPricingController {

	public static final Logger logger = LoggerFactory.getLogger(ProductPricingController.class);

	private final PricingService pricingService;

	@Autowired
	public ProductPricingController(PricingService pricingService) {
		this.pricingService = pricingService;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public HttpEntity<?> find(@PathVariable String id) {
		ProductPrice detail = pricingService.findOne(id);
		if (detail == null) {
			logger.error("ProductPrice with id {} not found.", id);
			return new ResponseEntity<ProductPricingException>(
					new ProductPricingException("ProductPrice with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ProductPrice>(detail, HttpStatus.OK);
	}

	@ApiOperation(value = "Create new ProductPrice", notes = "Creates new ProductPrice")
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.CREATED)
	public HttpEntity<?>  create(@RequestBody @Valid ProductPrice productPrice) {
		ProductPrice created = pricingService.save(productPrice);
		if (created == null) {
			logger.error("Product id {} not found in Product Catalogue", productPrice.getProductId());
			return new ResponseEntity<ProductPricingException>(
					new ProductPricingException("Product id " + productPrice.getProductId() + " not found in Product Catalogue"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ProductPrice>(productPrice, HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "Update a ProductPrice", notes = "Updates a ProductPrice")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public HttpEntity<?> update(@PathVariable String id, @Valid @RequestBody ProductPrice productPrice)
			throws IOException {
		ProductPrice updated = pricingService.update(id, productPrice);
		if (updated == null) {
			logger.error("ProductPrice with id {} not found.", id);
			return new ResponseEntity<ProductPricingException>(
					new ProductPricingException("ProductPrice with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ProductPrice>(updated, HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "Delete a ProductPrice", notes = "Deletes a ProductPrice")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public HttpEntity<?> delete(@PathVariable String id) {
		pricingService.delete(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
		messageBundle.setBasename("classpath:messages/error");
		messageBundle.setDefaultEncoding("UTF-8");
		return messageBundle;
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	static class ProductPricingException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String errorMessage;

		public ProductPricingException(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		@Override
		public StackTraceElement[] getStackTrace() {
			return null;
		}
	}

}
