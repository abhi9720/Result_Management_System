package com.nagarro.ProductSearchApi.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nagarro.ProductSearchApi.dto.ProductCodesDTO;
import com.nagarro.ProductSearchApi.dto.ProductPriceDTO;
import com.nagarro.ProductSearchApi.model.Product;
import com.nagarro.ProductSearchApi.model.Views;
import com.nagarro.ProductSearchApi.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	/*
	GET: Get all products
	*/
	@GetMapping("/products/all")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		return ResponseEntity.ok(products);
	}

	/*
	POST: Create a product
	*/
	@PostMapping("/products")
	public ResponseEntity<?> createProduct(
	        @Validated @RequestPart Product product,
	        @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
	        BindingResult bindingResult
	) {
	    // Check for all fields are present
	    if (bindingResult.hasErrors()) {
	        Map<String, Object> errorResponse = 	new HashMap<>();
	        for (FieldError error : bindingResult.getFieldErrors()) {
	            errorResponse.put(error.getField(), error.getDefaultMessage());
	        }
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    }
	    

	    if (imageFile != null && imageFile.getSize() > 1048576) {
	    	Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("message", "Maximum Image Size Allowed is 1MB");
	        errorResponse.put("Error", "FileSizeLimitExceeded");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    }
	    try {
	        Product createdProduct = productService.createProduct(product, imageFile);
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
	    } catch (DataIntegrityViolationException e) {
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("message", e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    }
	}

	/*
	PUT: Update a product
	*/
	@PutMapping("/products/{productCode}")
	public ResponseEntity<?> updateProduct(@PathVariable("productCode") String productCode,
			@RequestPart Product product,
			@RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
			BindingResult bindingResult) {
		try {
			// Check for all fields are present
			if (bindingResult.hasErrors()) {
				Map<String, Object> errorResponse = new HashMap<>();
				for (FieldError error : bindingResult.getFieldErrors()) {
					errorResponse.put(error.getField(), error.getDefaultMessage());
				}
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
			}

			if (imageFile != null && imageFile.getSize() > 1048576) {
				Map<String, Object> errorResponse = new HashMap<>();
				errorResponse.put("message", "Maximum Image Size Allowed is 1MB");
				errorResponse.put("Error", "FileSizeLimitExceeded");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
			}

			Product updatedProduct = productService.updateProduct(productCode, product, imageFile);
			return ResponseEntity.ok(updatedProduct);
		} catch (NoSuchElementException e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}

	/*
	DELETE: Delete a product
	*/
	@DeleteMapping("/products/{productCode}")
	public ResponseEntity<String> deleteProduct(@PathVariable("productCode") String productCode) {
		try {
			productService.deleteProduct(productCode);
			return ResponseEntity.ok("Product deleted successfully");
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	/*
	GET: Search products
	*/
	@GetMapping("/products")
	public ResponseEntity<?> searchProducts(@RequestParam(required = false) String name,
			@RequestParam(required = false) String productCode, @RequestParam(required = false) String brand,
			Authentication authentication) {
		try {
			List<Product> matchingProducts = productService.searchProducts(name, productCode, brand);
	        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(matchingProducts);

			if (!matchingProducts.isEmpty()) {
				System.out.println(authentication);
				if (authentication != null && authentication.isAuthenticated()) {
		            mappingJacksonValue.setSerializationView(Views.Authenticated.class);
		        } else {
		            mappingJacksonValue.setSerializationView(Views.Public.class);
		        }
				System.out.println(mappingJacksonValue);
		        return new ResponseEntity<>(mappingJacksonValue, HttpStatus.OK);
			} else {
				return ResponseEntity.noContent().build();
			}
		} catch (IllegalArgumentException e) {
			 Map<String, Object> errorResponse = new HashMap<>();
		        errorResponse.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}

	/*
	GET: Get product by product code
	*/
	@GetMapping("/products/{productCode}")
	public ResponseEntity<Product> getProductByProductCode(@PathVariable String productCode) {

		Product product = productService.getProductByProductCode(productCode);
		if (product != null) {
			return ResponseEntity.ok(product);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	/*
	GET: Get price by product code
	*/
	@GetMapping("/products/price/{productCode}")
	public ResponseEntity<ProductPriceDTO> getPrice(@PathVariable String productCode) {
		try {
		ProductPriceDTO price = productService.getPrice(productCode);
		return ResponseEntity.ok(price);
		
	}catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	/*
	POST: Get prices of multiple products
	*/
	@PostMapping("/products/price")
	public ResponseEntity<?> getPrices(@RequestBody ProductCodesDTO productCodesDTO) {
		List<String> productCodes = productCodesDTO.getProductCodes();
		System.out.println(productCodes);
		Map<String, Object> prices = productService.getPrices(productCodes);
		return ResponseEntity.ok(prices);
	}

}
