package com.eCommerce.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.eCommerce.controller.UserRatingControllerAPI;
import com.eCommerce.dto.UserRatingDto;
import com.eCommerce.modal.UserRatings;
import com.eCommerce.modal.service.UserRatingService;


@Controller
public class UserRatingController implements UserRatingControllerAPI {

	@Autowired
	private UserRatingService userRatingService;

	@Override
	public ResponseEntity<UserRatings> createUserRating(UserRatingDto userRatingDto) {
		UserRatings userRatings = this.userRatingService.createUserRating(userRatingDto);
		return new ResponseEntity<>(userRatings, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<UserRatings> updateUserRating(UserRatingDto userRatingDto, String userRatingId) {
		UserRatings userRatings = this.userRatingService.updateUserRating(userRatingId, userRatingDto);
		return new ResponseEntity<>(userRatings, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> getRatingsForProduct(String prodId) {
		return ResponseEntity.ok(this.userRatingService.getUserRatings(prodId));
	}
}
