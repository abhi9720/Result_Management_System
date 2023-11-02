package com.nagarro.ProductSearchApi.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nagarro.ProductSearchApi.dto.ProductPriceDTO;
import com.nagarro.ProductSearchApi.model.Product;
import com.nagarro.ProductSearchApi.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> searchProducts(String name, String productCode, String brand) {

		if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(productCode) && StringUtils.isNotBlank(brand)) {
			return productRepository.findByNameIgnoreCaseAndProductCodeIgnoreCaseAndBrandIgnoreCase(name, productCode,
					brand);
		} else if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(productCode)) {
			return productRepository.findByNameIgnoreCaseAndProductCodeIgnoreCase(name, productCode);
		} else if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(brand)) {
			return productRepository.findByNameIgnoreCaseAndBrandIgnoreCase(name, brand);
		} else if (StringUtils.isNotBlank(productCode) && StringUtils.isNotBlank(brand)) {
			return productRepository.findByProductCodeIgnoreCaseAndBrandIgnoreCase(productCode, brand);
		} else if (StringUtils.isNotBlank(name)) {
			return productRepository.findByNameIgnoreCase(name);
		} else if (StringUtils.isNotBlank(productCode)) {
			return productRepository.findByProductCodeIgnoreCase(productCode);
		} else if (StringUtils.isNotBlank(brand)) {
			return productRepository.findByBrandIgnoreCase(brand);
		} else {
			throw new IllegalArgumentException("At least one search parameter is required.");
		}

	}

	@Override
	public Product getProductByProductCode(String productCode) {
		return productRepository.findByProductCode(productCode);
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product createProduct(Product product, MultipartFile imageFile) {
		// Check if product with the same ProductCode already exists
		if (productRepository.existsByProductCode(product.getProductCode())) {
			throw new DataIntegrityViolationException("Product with the same ProductCode already exists");
		}

		try {
			// Set the image data from the uploaded file
			if (imageFile != null) {
				product.setImage(imageFile.getBytes());
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to process the image");
		}

		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(String productCode, Product updatedProduct, MultipartFile imageFile) {
		Product existingProduct = getProductByProductCode(productCode);
		if (existingProduct == null) {
			throw new NoSuchElementException("No Product found for ProductCode: " + productCode);
		}

		if (StringUtils.isNotBlank(updatedProduct.getName())) {
			existingProduct.setName(updatedProduct.getName());
		}

		if (StringUtils.isNotBlank(updatedProduct.getProductCode())) {
			existingProduct.setProductCode(updatedProduct.getProductCode());
		}

		if (StringUtils.isNotBlank(updatedProduct.getBrand())) {
			existingProduct.setBrand(updatedProduct.getBrand());
		}

		if (StringUtils.isNotBlank(updatedProduct.getDescription())) {
			existingProduct.setDescription(updatedProduct.getDescription());
		}

		if (updatedProduct.getPrice() != null) {
			existingProduct.setPrice(updatedProduct.getPrice());
		}

		try {
			// Set the image data from the uploaded file
			if (imageFile != null) {
				existingProduct.setImage(imageFile.getBytes());
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to process the image");
		}

		return productRepository.save(existingProduct);
	}

	@Override
	public void deleteProduct(String productCode) {
		Product product = this.getProductByProductCode(productCode);
		if (product == null) {
			throw new NoSuchElementException("Product not found with ID: " + productCode);
		}
		productRepository.delete(product);
	}

	@Override
	public ProductPriceDTO getPrice(String productCode) {
		Product product = productRepository.findByProductCode(productCode);
		if (product != null) {
			return new ProductPriceDTO(product.getPrice(), product.getProductCode());
		} else {
			throw new NoSuchElementException("Product not found with ID: " + productCode);
		}
	}

	@Override
	public Map<String, Object> getPrices(List<String> productCodes) {
		List<Product> products = productRepository.findByProductCodeIn(productCodes);
		Map<String, Object> result = new HashMap<>();
		for (Product p : products) {
			result.put(p.getProductCode(), p.getPrice());
		}
		return result;
	}

}
