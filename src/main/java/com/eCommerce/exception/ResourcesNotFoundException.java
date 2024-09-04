package com.eCommerce.exception;

@SuppressWarnings("serial")
public class ResourcesNotFoundException extends RuntimeException {

	private final String resourceName;
	private final String fieldName;
	private final String fieldValue;
	
	public ResourcesNotFoundException(String resourceName, String fieldName, String uid) {
		super(String.format("%s not found with %s : %s",resourceName, fieldName, uid));
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
