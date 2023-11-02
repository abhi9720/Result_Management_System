package com.nagarro.ProductSearchApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nagarro.ProductSearchApi.model.Availability;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

	Availability findByProduct_productCodeAndPincode(String productCode, String pincode);
}
