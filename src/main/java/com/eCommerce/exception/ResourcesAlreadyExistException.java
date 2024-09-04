package com.eCommerce.exception;

public class ResourcesAlreadyExistException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private final String resourceName;
	private final String fieldName;
	private final String fieldValue;
	
	public ResourcesAlreadyExistException(String resourceName, String fieldName, String uid) {
		super(String.format("!! %s (%s) is already present in %s !!", fieldName, uid, resourceName));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = uid;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}
}
