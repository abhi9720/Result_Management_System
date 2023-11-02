package com.nagarro.ProductSearchApi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nagarro.ProductSearchApi.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByNameIgnoreCase(String name);

	List<Product> findByProductCodeIgnoreCase(String code);

	List<Product> findByBrandIgnoreCase(String brand);

	List<Product> findByNameIgnoreCaseAndProductCodeIgnoreCase(String name, String code);

	List<Product> findByNameIgnoreCaseAndBrandIgnoreCase(String name, String brand);

	List<Product> findByProductCodeIgnoreCaseAndBrandIgnoreCase(String code, String brand);

	List<Product> findByNameIgnoreCaseAndProductCodeIgnoreCaseAndBrandIgnoreCase(String name, String code, String brand);

//	List<Product> findAllByProductCode(List<String> productCode);
	
	Product findByProductCode(String code);

	boolean existsByProductCode(String code);

	List<Product> findByProductCodeIn(List<String> productCodes);
}
