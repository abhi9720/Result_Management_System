package com.nagarro.ProductSearchApi.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.ProductSearchApi.model.Availability;
import com.nagarro.ProductSearchApi.model.Product;
import com.nagarro.ProductSearchApi.repository.AvailabilityRepository;
import com.nagarro.ProductSearchApi.repository.ProductRepository;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

	@Autowired
	private AvailabilityRepository availabilityRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Availability checkProductAvailability(String productCode, String pincode) {

		Product product = productRepository.findByProductCode(productCode);
		if (product == null) {
			throw new NoSuchElementException("Invalid product code");
		}

		Availability availability = availabilityRepository.findByProduct_productCodeAndPincode(productCode, pincode);
		System.out.println("findByProduct_productCodeAndPincode :   " + availability);

		return availability;

	}

}
