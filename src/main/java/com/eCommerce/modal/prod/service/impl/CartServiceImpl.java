package com.eCommerce.modal.prod.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.dto.CartDto;
import com.eCommerce.exception.ResourcesAlreadyExistException;
import com.eCommerce.exception.ResourcesNotFoundException;
import com.eCommerce.modal.User;
import com.eCommerce.modal.prod.Cart;
import com.eCommerce.modal.prod.CartItems;
import com.eCommerce.modal.prod.Product;
import com.eCommerce.modal.prod.ProductSize;
import com.eCommerce.modal.prod.repository.CartItemsRepository;
import com.eCommerce.modal.prod.repository.CartRepository;
import com.eCommerce.modal.prod.service.CartService;
import com.eCommerce.modal.prod.service.ProductService;
import com.eCommerce.modal.prod.service.ProductSizeService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductSizeService productSizeService;
	
	@Autowired
	private CartItemsRepository cartItemsRepository;
	
	@Override
	public Cart createCartOfUser(User user) {
		
		LocalDateTime nowDate = LocalDateTime.now();
		
		Cart cart = new Cart();
		cart.setCreateDate(nowDate);
		cart.setUpdateDate(nowDate);
		cart.setUser(user);
		cart.setTotal(0);
		
		cart = this.cartRepository.save(cart);
		return cart;
	}
	
	@Override
	public Cart addToCart(CartDto cartDto) {
		User user = cartDto.getUser();
		Product product = this.productService.getProduct(cartDto.getProductId());
		ProductSize size = this.productSizeService.getProductSize(cartDto.getProductSizeId());
		
		LocalDateTime nowDate = LocalDateTime.now();
		
		Cart cart = this.getCartByUser(user);
		
		CartItems cartItems = this.getCartItemsByProductAndSize(cart, product, size);
		if(cartItems == null) {
			cartItems = new CartItems();
			cartItems.setCart(cart);
			cartItems.setProduct(product);
			cartItems.setProductSize(size);
			cartItems.setQuantity(cartDto.getQuantity());
			cartItems.setCreateDate(nowDate);
			cartItems.setUpdateDate(nowDate);
			
			cartItems = this.cartItemsRepository.save(cartItems);
			
			cart.setUpdateDate(nowDate);
			cart.setTotal(product.getPrice() + cart.getTotal());
			cart = this.cartRepository.save(cart);
		}
		else {
			throw new ResourcesAlreadyExistException("[Cart], [Product], [ProductSize]", 
					"CartItem", 
					"[ "+cart.getId()+"], [ "+product.getId()+" ], [ "+size.getId()+" ]");
		}
		return cart;
	}

	@Override
	public Cart updateCartQuantity(String cartId, CartDto cartDto) {
		User user = cartDto.getUser();
		Product product = 
				this.productService.getProduct(cartDto.getProductId());
		ProductSize productSize = 
				this.productSizeService.getProductSize(cartDto.getProductSizeId());
		
		Cart cart = this.getCartByUser(user);
		CartItems cartItems = null;
		
		if(cart != null) {
			cartItems = this.getCartItemsByProductAndSize(cart, product, productSize);
			if(cartItems != null) {
				double total = cart.getTotal() - (cartItems.getQuantity()*product.getPrice());
				total += cartDto.getQuantity()*product.getPrice();
				
				cartItems.setQuantity(cartDto.getQuantity());
				cartItems.setUpdateDate(LocalDateTime.now());
				cartItems = this.cartItemsRepository.save(cartItems);
				
				cart.setTotal(total);
				cart.setUpdateDate(LocalDateTime.now());
				cart = this.cartRepository.save(cart);
			}
		}
		
		return cart;
	}
	
	@Override
	public Cart getCartByUser(User user) {
		Cart cart = this.cartRepository.findByUser(user)
						.orElseThrow(() -> new ResourcesNotFoundException("Cart", "User", user.getUsername()));
		return cart;
	}

	@Override
	public CartItems getCartItemsByProduct(Cart cart, Product product) {
		Optional<CartItems> cartItem = 
				this.cartItemsRepository.findCartItemsByCartAndProduct(cart, product);
		
		if(cartItem == null) {
			return null;
		}
		return cartItem.get();
	}
	
	@Override
	public CartItems getCartItemsByProductAndSize(Cart cart, Product product, ProductSize productSize) {
		Optional<CartItems> cartItem = 
				this.cartItemsRepository.findCartItemsByCartAndProductAndProductSize(cart, product, productSize);
		
		if(cartItem.isEmpty()) {
			return null;
		}
		return cartItem.get();
	}

	@Override
	public Cart getCartByUserAndId(String cartId, User user) {
		Optional<Cart> oCart = 
				this.cartRepository.findByIdAndUser(cartId, user);
		if(oCart.isEmpty())
			return null;
		
		return oCart.get();
	}
	
	@Override
	public List<CartItems> getAllCartItems(User user) {
		Cart cart = this.getCartByUser(user);
		List<CartItems> cartItems = this.cartItemsRepository.findByCart(cart);
		return cartItems;
	}

	@Override
	public boolean removeCartItem(String cartItemId) {

		Optional<CartItems> oCartItem = 
				this.cartItemsRepository.findById(cartItemId);
		
		if(oCartItem.isEmpty()) {
			throw new ResourcesNotFoundException("Cart Item", "id", cartItemId);
		}
		try {
			CartItems cartItems = oCartItem.get();
			Cart cart = cartItems.getCart();
			
			double total = cartItems.getProduct().getPrice();
			total = total * cartItems.getQuantity();
			
			if(cartItemId != null) {
				this.cartItemsRepository.delete(cartItems);
				
				cart.setUpdateDate(LocalDateTime.now());
				double cartTotal = cart.getTotal() - total;
				cart.setTotal(cartTotal >= 0 ? cartTotal : 0);
				
				this.cartRepository.save(cart);
			}
		} catch (Exception ex) {
			System.err.println(ex.getClass().getName());
			return false;
		}
		return true;
	}
	
	@Override
	public Cart getCartDetails(User user) {
		Cart cart = this.getCartByUser(user);
		return cart;
	}
	
	@Override
	public Cart updateCartTotalAmount(User user) {
		Cart cart = this.getCartByUser(user);
		List<CartItems> cartItems = this.cartItemsRepository.findByCart(cart);
		if(cartItems.size() == 0) {
			cart.setTotal(0);
			cart.setUpdateDate(LocalDateTime.now());
			this.cartRepository.save(cart);
		} 
		else {
			cartItems.forEach((item) -> {
				cart.setTotal(
						cart.getTotal()+
						(item.getQuantity()*item.getProduct().getPrice()));
			});
			cart.setUpdateDate(LocalDateTime.now());
		}
		return null;
	}

}
