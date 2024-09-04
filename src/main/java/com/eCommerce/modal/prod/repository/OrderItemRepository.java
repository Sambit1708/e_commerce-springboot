package com.eCommerce.modal.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eCommerce.modal.prod.OrderItems;
import java.util.List;
import java.util.Map;

import com.eCommerce.modal.prod.Order;


public interface OrderItemRepository extends JpaRepository<OrderItems, String> {

	List<OrderItems> findByOrder(Order order);
	
	@Query(value = "SELECT ot.id, ot.quantity, ot.price, ot.create_date, ot.update_date, "
					+ "ot.product_id, ot.product_size_id, ot.order_id, o.delivery_date "
					+ "FROM order_items ot JOIN orders o ON ot.order_id=o.id WHERE "
					+ "user_id IN(SELECT id FROM users WHERE email=:email);", nativeQuery = true)
	List<Map<String, Object>> findOrderItemByUser(@Param("email") String email);
}
