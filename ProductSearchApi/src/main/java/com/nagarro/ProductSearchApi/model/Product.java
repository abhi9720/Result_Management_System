package com.nagarro.ProductSearchApi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @JsonView(Views.Public.class)
	@NotBlank(message = "Name is required")
    private String name;

    @JsonView(Views.Public.class)
    @NotNull
    @Column(name = "product_code", unique = true, nullable = false)
	@NotBlank(message = "productCode is required")
    private String productCode;

    @JsonView(Views.Public.class)
	@NotBlank(message = "Brand is required")
    private String brand;

    @JsonView(Views.Authenticated.class)
    @Column(name="description", length=1000)
    private String description;
    
    @JsonView(Views.Authenticated.class)
    @NotNull(message = "Price is required")
    private Double price;
    
    @JsonView(Views.Public.class)
    @Lob
    private byte[] image;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
