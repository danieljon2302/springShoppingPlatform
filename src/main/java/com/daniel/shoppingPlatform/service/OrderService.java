package com.daniel.shoppingPlatform.service;

import java.util.List;

import com.daniel.shoppingPlatform.model.Order;

import dto.CreateOrderRequest;
import dto.OrderQueryParams;

public interface OrderService {
	
	Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

	Order getOrderById(Integer orderId);
	
	Integer countOrder(OrderQueryParams orderQueryParams);
	
	List<Order> getOrders(OrderQueryParams orderQueryParams);
}
