package com.nagarro.ProductSearchApi.exception;

public class DuplicateEmailException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2440834209657815117L;

	public DuplicateEmailException(String message) {
		super(message);
	}
}
