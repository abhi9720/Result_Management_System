package com.nagarro.ProductSearchApi.dto;

public class ProductPriceDTO {
	Double price;
	String productCode;

	public ProductPriceDTO() {
		super();
	}

	public ProductPriceDTO(Double price, String productCode) {
		super();
		this.price = price;
		this.productCode = productCode;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

}
