package com.nagarro.ProductSearchApi.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.nagarro.ProductSearchApi.dto.ProductPriceDTO;
import com.nagarro.ProductSearchApi.model.Product;

public interface ProductService {

	List<Product> searchProducts(String name, String code, String brand);

	Product getProductByProductCode(String productCode);

	List<Product> getAllProducts();

	Product createProduct(Product product, MultipartFile imageFile);

	Product updateProduct(String productCode, Product updatedProduct, MultipartFile imageFile);

	void deleteProduct(String productCode);

	ProductPriceDTO getPrice(String productCode);

	Map<String, Object> getPrices(List<String> productCode);

}
