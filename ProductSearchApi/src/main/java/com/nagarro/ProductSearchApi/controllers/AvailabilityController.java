package com.nagarro.ProductSearchApi.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.ProductSearchApi.dto.AvailabilityResponse;
import com.nagarro.ProductSearchApi.model.Availability;
import com.nagarro.ProductSearchApi.service.AvailabilityService;

@RestController
public class AvailabilityController {

	@Autowired
	private AvailabilityService availabilityService;

	/*
	GET: Check product availability
	*/
	@GetMapping("/product-available")
	public ResponseEntity<?> checkProductAvailability(@RequestParam String productCode, @RequestParam String pincode) {
        Map<String, Object> errorResponse = new HashMap<>();
		try {
	        Availability availability = availabilityService.checkProductAvailability(productCode, pincode);

	        System.out.println("availability => "+availability);
	        // For not registered pin code
	        if (availability == null || !availability.isServiceable()) {
	            errorResponse.put("message", "Product not available at the given pincode");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	        } else {
	            AvailabilityResponse response = new AvailabilityResponse(availability.isServiceable(), availability.getDeliveryTime());
	            return ResponseEntity.ok(response);
	        }
	    } catch (NoSuchElementException err) {
	        errorResponse.put("error", err.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	    }
	}


}
