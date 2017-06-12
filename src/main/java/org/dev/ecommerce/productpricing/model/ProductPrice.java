package org.dev.ecommerce.productpricing.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class ProductPrice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8666986134942760654L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", price=" + price + ", description=" + description + "]";
	}

	@NotNull(message = "error.productId.notnull")
	@Size(min = 11, max = 11, message = "error.productId.size")
	@Id
	private String productId;

	@NotNull(message = "error.price.notnull")
	private String price;

	private String description;

	public ProductPrice() {
	}

	public ProductPrice(String productId, String price, String description) {
		this.productId = productId;
		this.price = price;
		this.description = description;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
