package com.eCommerce.modal.prod.service;

import java.util.List;

import com.eCommerce.dto.CartDto;
import com.eCommerce.modal.User;
import com.eCommerce.modal.prod.Cart;
import com.eCommerce.modal.prod.CartItems;
import com.eCommerce.modal.prod.Product;
import com.eCommerce.modal.prod.ProductSize;

import jakarta.transaction.Transactional;

public interface CartService {

	public Cart createCartOfUser(User user);
	
	public Cart addToCart(CartDto cartDtos);
	
	public CartItems getCartItemsByProduct(Cart cart, Product product);
	
	public CartItems getCartItemsByProductAndSize(Cart cart, Product product, ProductSize productSize);
	
	public Cart getCartByUser(User user);
	
	public Cart updateCartQuantity(String cartId, CartDto cartDto);
	
	public Cart getCartByUserAndId(String id, User user);
	
	public List<CartItems> getAllCartItems(User user);
	
	@Transactional
	public boolean removeCartItem(String cartItemId);
	
	public Cart getCartDetails(User user);
	
	public Cart updateCartTotalAmount(User user);
}
