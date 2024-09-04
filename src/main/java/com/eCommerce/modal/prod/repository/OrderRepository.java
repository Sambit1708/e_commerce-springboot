package com.eCommerce.modal.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eCommerce.modal.prod.Order;
import com.eCommerce.modal.User;
import java.util.List;
import java.util.Map;

public interface OrderRepository extends JpaRepository<Order, String> {

	List<Order> findByUser(User user);

	@Query(value = "SELECT COUNT(*) AS total, " + "SUM(total_amount) AS total_sum, "
			+ "ROUND(AVG(total_amount)) AS avg_amount FROM orders", nativeQuery = true)
	Map<String, Object> findByTotalAvgOrders();

	@Query(value = "SELECT DATE_FORMAT(created_date, '%Y-%m') AS MONTH, SUM(total_amount) AS total_sum,"
			+ "ROUND(AVG(total_amount)) AS avg_sell, COUNT(*) AS total FROM orders "
			+ "GROUP BY DATE_FORMAT(created_date, '%Y-%m') ORDER BY MONTH DESC", nativeQuery = true)
	List<Map<String, Object>> findByYearMonthWiseSell();

	@Query(value = "SELECT bd.name, COUNT(*) AS total_order, SUM(otm.price * otm.quantity) AS total_sell "
			+ "FROM `orders` AS od INNER JOIN order_items AS otm ON od.id = otm.order_id "
			+ "INNER JOIN product AS pd ON pd.id = otm.product_id INNER JOIN brand AS bd "
			+ "ON bd.id = pd.brand_id GROUP BY bd.name", nativeQuery = true)
	List<Map<String, Object>> findByBrandWiseSell();
}
