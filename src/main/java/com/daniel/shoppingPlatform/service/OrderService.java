package com.daniel.shoppingPlatform.service;

import com.daniel.shoppingPlatform.model.Order;

import dto.CreateOrderRequest;

public interface OrderService {
	
	public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

	public Order getOrderById(Integer orderId);
}
