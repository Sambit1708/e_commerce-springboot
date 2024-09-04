package com.eCommerce.modal.service;

import java.util.List;

import com.eCommerce.dto.UserRatingDto;
import com.eCommerce.modal.UserRatings;

public interface UserRatingService {

	public UserRatings createUserRating(UserRatingDto userRatingDto);
	
	public UserRatings updateUserRating(String userRatingId,UserRatingDto userRatingDto);
	
	public List<UserRatings> getUserRatings(String productId);
	
	public Double getAvgRatingForProduct(String productId);
	
	public void deleteUserRatings(String userId, String productId);
	
	public UserRatings getProductsUserRating(String userId, String productId);
	
	public UserRatings getUserRating(String userRatingId);
}
