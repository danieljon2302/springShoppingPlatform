package com.daniel.shoppingPlatform.service;

import dto.CreateOrderRequest;

public interface OrderService {
	
	public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

}
