package com.eCommerce.controller.impl;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import com.eCommerce.controller.CartControllerAPI;
import com.eCommerce.dto.CartDto;
import com.eCommerce.modal.User;
import com.eCommerce.modal.prod.Cart;
import com.eCommerce.modal.prod.CartItems;
import com.eCommerce.modal.prod.service.CartService;
import com.eCommerce.modal.service.UserService;
import com.eCommerce.payload.ApiResponseMessage;
import com.eCommerce.payload.ECommerceConstant;


@Controller
public class CartController implements CartControllerAPI {

	private final CartService cartService;
	
	private UserService userService;
	
	public CartController(CartService cartService, UserService userService) {
		this.cartService = cartService;
		this.userService = userService;
	}
	
	
	@Override
	public ResponseEntity<List<CartItems>> getAllCartItems(Principal principal) {
		User user = this.userService.getUser(principal.getName());
		List<CartItems> cartItems = this.cartService.getAllCartItems(user);
		return ResponseEntity.ok(cartItems);
	}
	
	@Override
	public ResponseEntity<Cart> addToCart(Principal principal, CartDto cartDto) {
		User user = this.userService.getUser(principal.getName());
		cartDto.setUser(user);
		Cart cart = this.cartService.addToCart(cartDto);
		return new ResponseEntity<Cart>(cart, HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<Cart> updateCartQuantity(Principal principal, CartDto cartDto, String cartId) {
		User user = this.userService.getUser(principal.getName());
		cartDto.setUser(user);
		Cart cart = this.cartService.updateCartQuantity(cartId, cartDto);
		return new ResponseEntity<Cart>(cart, HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<ApiResponseMessage> removeCartItem(String cartItemId) 
	{
		boolean cartItemRemoveStatus = this.cartService.removeCartItem(cartItemId);
		
		ApiResponseMessage apiMessage = 
						ApiResponseMessage.builder()
											.status(false)
											.message(ECommerceConstant.PROBLEM_MSG)
											.statusCode(ECommerceConstant.INTERNAL_SERVER_ERROR)
											.build();
		if(cartItemRemoveStatus) {
			apiMessage.setStatus(true);
			apiMessage.setMessage(ECommerceConstant.CART_ITEM_REMOVE_MSG);
			apiMessage.setStatusCode(ECommerceConstant.OK);
			
			return new ResponseEntity<ApiResponseMessage>(apiMessage, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<ApiResponseMessage>(apiMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@Override
	public ResponseEntity<Cart> getCartDetail(Principal principal) 
	{
		User user = this.userService.getUser(principal.getName());
		Cart cart = this.cartService.getCartDetails(user);
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}
}
