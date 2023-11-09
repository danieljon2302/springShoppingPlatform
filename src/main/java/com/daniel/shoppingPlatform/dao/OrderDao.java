package com.daniel.shoppingPlatform.dao;

import java.util.List;

import com.daniel.shoppingPlatform.model.Order;
import com.daniel.shoppingPlatform.model.OrderItem;

public interface OrderDao {
	
	Order getOrderById(Integer orderId);
	
	List<OrderItem> getOrderItemsByOrderId(Integer orderId);

	Integer createOrder(Integer userId, Integer totalAmount);
	
	void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
	
}
