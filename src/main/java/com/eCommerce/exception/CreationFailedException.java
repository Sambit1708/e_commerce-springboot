package com.eCommerce.exception;

@SuppressWarnings("serial")
public class CreationFailedException extends RuntimeException {

	private final String entityName;
	
	private final String uniqueField;
	
	private final String fieldValue;

	public CreationFailedException(String entityName, String uniqueField, String fieldValue) {
		super(String.format("Failed to create %s with %s : %s", entityName, uniqueField, fieldValue));
		this.entityName = entityName;
		this.uniqueField = uniqueField;
		this.fieldValue = fieldValue;
	}

	public String getEntityName() {
		return entityName;
	}

	public String getUniqueField() {
		return uniqueField;
	}
	
	public String getFieldValue() {
		return fieldValue;
	}
}
