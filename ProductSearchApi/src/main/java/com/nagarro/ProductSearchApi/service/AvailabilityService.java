package com.nagarro.ProductSearchApi.service;

import com.nagarro.ProductSearchApi.model.Availability;

public interface AvailabilityService {

	Availability checkProductAvailability(String productCode, String pincode);

}
