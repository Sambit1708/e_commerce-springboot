package com.eCommerce.modal.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.dto.UserRatingDto;
import com.eCommerce.exception.ResourcesNotFoundException;
import com.eCommerce.modal.User;
import com.eCommerce.modal.UserRatings;
import com.eCommerce.modal.prod.Product;
import com.eCommerce.modal.prod.service.ProductService;
import com.eCommerce.modal.repository.UserRatingsRepository;
import com.eCommerce.modal.service.UserRatingService;
import com.eCommerce.modal.service.UserService;

@Service
public class UserRatingServiceImpl implements UserRatingService {

	@Autowired
	private UserRatingsRepository userRatingsRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserRatings createUserRating(UserRatingDto userRatingDto) {
		Product product = this.productService.getProduct(userRatingDto.getProductId());
		User user = this.userService.getUserById(userRatingDto.getUserId());
		UserRatings userRatings = new UserRatings();
		userRatings.setRating(userRatingDto.getRating());
		userRatings.setReview(userRatingDto.getReview());
		userRatings.setProduct(product);
		userRatings.setUser(user);
		userRatings = this.userRatingsRepository.save(userRatings);
		
		// Saving Average User Rating for this product
		double avgRating = this.getAvgRatingForProduct(userRatingDto.getProductId());
		double parsedRating = Double.parseDouble(String.format("%.1f", avgRating));
		product.setRating(parsedRating);
//		product = this.productService.updateProduct(product);
		
		return userRatings;
		
	}

	@Override
	public UserRatings updateUserRating(String userRatingId, UserRatingDto userRatingDto) {
		UserRatings userRating = this.getUserRating(userRatingId);
		userRating = this.modelMapper.map(userRatingDto, UserRatings.class);
		return this.userRatingsRepository.save(userRating);
	}

	
	@Override
	public List<UserRatings> getUserRatings(String productId) {
		Product product = this.productService.getProduct(productId);
		return this.userRatingsRepository.findByProduct(product);
	}

	@Override
	public Double getAvgRatingForProduct(String productId) {
		List<UserRatings> userRatings = this.getUserRatings(productId);
		if(userRatings.size() == 0) {
			return 0.0;
		}
		
		int result = userRatings.stream().mapToInt(UserRatings::getRating).sum();
		
		return Double.valueOf(result/(double)userRatings.size());
	}

	@Override
	public void deleteUserRatings(String userId, String productId) {
		UserRatings userRatings = this.getProductsUserRating(userId, productId);
		this.userRatingsRepository.delete(userRatings);
	}

	@Override
	public UserRatings getProductsUserRating(String userId, String productId) {
		User user = this.userService.getUserById(productId);
		Product product = this.productService.getProduct(productId);
		UserRatings userRatings = this.userRatingsRepository.findByProductAndUser(product, user);
		return userRatings;
	}

	@Override
	public UserRatings getUserRating(String userRatingId) {
		UserRatings userRatings = this.userRatingsRepository.findById(userRatingId).orElseThrow(
				() -> new ResourcesNotFoundException("UserRatings" , "userRatingId", userRatingId)
		);
		return userRatings;
	}

}
