package com.nagarro.ProductSearchApi.dto;

public class AvailabilityResponse {

	private boolean isServiceable;
	private String deliveryTime;

	public AvailabilityResponse() {
		super();
	}

	public AvailabilityResponse(boolean isServiceable, String deliveryTime) {
		super();
		this.isServiceable = isServiceable;
		this.deliveryTime = deliveryTime;
	}

	public boolean isServiceable() {
		return isServiceable;
	}

	public void setServiceable(boolean isServiceable) {
		this.isServiceable = isServiceable;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

}
