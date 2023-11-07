package com.daniel.shoppingPlatform.dao;

import java.util.List;

import com.daniel.shoppingPlatform.model.OrderItem;

public interface OrderDao {

	Integer createOrder(Integer userId, Integer totalAmount);
	
	void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
	
}
