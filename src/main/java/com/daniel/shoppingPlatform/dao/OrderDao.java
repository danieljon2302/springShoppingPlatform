package com.daniel.shoppingPlatform.dao;

import java.util.List;

import com.daniel.shoppingPlatform.model.Order;
import com.daniel.shoppingPlatform.model.OrderItem;

import dto.OrderQueryParams;

public interface OrderDao {
	
	Integer countOrder(OrderQueryParams orderQueryParams);
	
	List<Order> getOrders(OrderQueryParams orderQueryParams);
	
	Order getOrderById(Integer orderId);
	
	List<OrderItem> getOrderItemsByOrderId(Integer orderId);

	Integer createOrder(Integer userId, Integer totalAmount);
	
	void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
	
	
	
}
