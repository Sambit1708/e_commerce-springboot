package com.eCommerce.dto;

import jakarta.validation.constraints.NotNull;

public class UserRatingDto {

	private int rating;
	private String review;
	
	@NotNull(message = "Product Id can not be null.")
	private String productId;
	
	@NotNull(message = "User Id can not be null.")
	private String userId;

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
