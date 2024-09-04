package com.eCommerce.modal.prod.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.eCommerce.dto.OrderDto;
import com.eCommerce.dto.OrderItemSendDto;
import com.eCommerce.dto.OrderItemsDto;
import com.eCommerce.exception.ResourcesNotFoundException;
import com.eCommerce.modal.Address;
import com.eCommerce.modal.User;
import com.eCommerce.modal.prod.Order;
import com.eCommerce.modal.prod.OrderItems;
import com.eCommerce.modal.prod.OrderStatus;
import com.eCommerce.modal.prod.repository.OrderRepository;
import com.eCommerce.modal.prod.service.CartService;
import com.eCommerce.modal.prod.service.OrderItemService;
import com.eCommerce.modal.prod.service.OrderService;
import com.eCommerce.modal.prod.service.PaymentService;
import com.eCommerce.modal.service.AddressService;
import com.eCommerce.modal.service.UserService;
import com.eCommerce.payload.ApiResponseMessage;
import com.eCommerce.payload.ECommerceConstant;

@Service
public class OrderServiceImpl implements OrderService {
	
	private final UserService userService;
	
	private final OrderRepository orderRepository;
	
	private final OrderItemService orderItemService;
	
	private final AddressService addressService;

	private final CartService cartService;
	
	private final PaymentService paymentService;
	
	public OrderServiceImpl(UserService userService, OrderRepository orderRepository, OrderItemService orderItemService,
			AddressService addressService, CartService cartService, PaymentService paymentService) {
		super();
		this.userService = userService;
		this.orderRepository = orderRepository;
		this.orderItemService = orderItemService;
		this.addressService = addressService;
		this.cartService = cartService;
		this.paymentService = paymentService;
	}

	@Override
	public Order order(String email, OrderDto orderDto) {
		
		LocalDateTime nowDate = LocalDateTime.now();
		User user = this.userService.getUser(email);
		Address address = this.addressService.getAddress(orderDto.getAddressId());
		
		double totalAmount = this.getTotalAmount(orderDto.getOrderItemsDtos());
		
		Order order = new Order();
		order.setDeliveryDate(nowDate.plusDays(7));
		order.setUser(user);
		order.setAddress(address);
		order.setTotalAmount(totalAmount);
		order.setOrderStatus(OrderStatus.CONFIRMED);
		order.setCreatedDate(nowDate);
		order.setUpdateDate(nowDate);
		
		order = this.orderRepository.save(order);
		List<OrderItems> orderItems =
				this.orderItemService.createOrderItems(order, orderDto.getOrderItemsDtos());
		order.setOrderItems(orderItems);
		
		this.paymentService.createPayment(user, order, orderDto.getPaymentMode());
		
		orderDto.getCartItemIds().forEach(item -> this.cartService.removeCartItem(item));
		
		this.cartService.updateCartTotalAmount(user);
		
		return order;
	}

	/**
	 * This method is user to get total amount from Price and Quantity
	 * @param orderItemsDtos
	 * @return List of Order
	 */
	private double getTotalAmount(List<OrderItemsDto> orderItemsDtos) {
		
		return orderItemsDtos.stream()
								.mapToDouble(item->
											(item.getQuantity() * item.getPrice()))
								.sum();
	}

	@Override
	public ApiResponseMessage updateOrderStatus(String orderId, OrderStatus orderStatus) {
		ApiResponseMessage apiMessage = ApiResponseMessage.builder()
											.status(true)
											.message(ECommerceConstant.ORDER_STATUS_UPDATE_MSG)
											.statusCode(ECommerceConstant.OK)
											.build();
		
		Order order = this.getOrder(orderId);
		if(order == null) {
			return ApiResponseMessage.builder()
					.status(false)
					.message("Order Id is Incorrect")
					.statusCode(ECommerceConstant.INTERNAL_SERVER_ERROR)
					.build();
		}
		
		order.setUpdateDate(LocalDateTime.now());
		order.setOrderStatus(orderStatus);
		this.orderRepository.save(order);
		return apiMessage;
	}
	
	public Order getOrder(String orderId) {
		return this.orderRepository.findById(orderId).orElseThrow(
				() -> new ResourcesNotFoundException("Order", "Order Id", orderId)
		);
	}
	
	@Override
	public List<Order> getAllOrdersOfUser(String email) {
		User user = this.userService.getUser(email);
		return this.orderRepository.findByUser(user);
	}
	
	/**
	 * This method is used to retrieve all OrderItems By Order
	 * @param orderId
	 * @return List of OrderItems
	 */
	@Override
	public List<OrderItems> getAllOrderItemsById(String orderId) {
		Order order = this.getOrder(orderId);
		return this.orderItemService.getAllOrderItemsOfOrder(order);
	}

	
	@Override
	public List<OrderItemSendDto> getAllOrderItemsOfOrder(String email) {
		return this.orderItemService.getAllOrderItemsOfOrder(email);
	}


	
	@Override
	public OrderItems getOrderItemById(String orderItemId) {
		return this.orderItemService.getOrderItemById(orderItemId);
	}

	/**
	 * This method is used to extract total average order
	 */
	@Override
	public Map<String, Object> getTotalAverageOrder() {
		return this.orderRepository.findByTotalAvgOrders();
	}
	
	/**
	 * This method is used to extract Year and month wise sell
	 */
	@Override
	public List<Map<String, Object>> getYearAndMonthWise() {
		return this.orderRepository.findByYearMonthWiseSell();
	}

	/**
	 * This method is used to extract total sell by brands
	 */
	@Override
	public List<Map<String, Object>> getBrandWiseSell() {
		return this.orderRepository.findByBrandWiseSell();
	}

	@Override
	public List<Order> getAllOrders() {
		return this.orderRepository.findAll();
	}
}
