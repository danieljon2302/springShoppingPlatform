package com.daniel.shoppingPlatform.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.daniel.shoppingPlatform.dao.OrderDao;
import com.daniel.shoppingPlatform.dao.ProductDao;
import com.daniel.shoppingPlatform.model.Order;
import com.daniel.shoppingPlatform.model.OrderItem;
import com.daniel.shoppingPlatform.model.Product;

import dto.BuyItem;
import dto.CreateOrderRequest;

@Component
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private ProductDao productDao;

	//注意: 在操作多張table時(createOrder, createOrderItem), 一定要加上Transational的註解, 
	//萬一噴出exception, 則可以復原( 確保兩個table是同時發生/ 不發生)
	@Transactional
	@Override
	public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
		//已從前端獲得productId與quantity, 現需自己產生一func計算總額amount(order table)
		int totalAmount = 0;
		List<OrderItem> orderItemList = new ArrayList<>();
		
		for(BuyItem buyItem : createOrderRequest.getBuyItemList()) {
			Product product = productDao.getProductById(buyItem.getProductId());
			
			//計算總金額
			int amount = buyItem.getQuantity() * product.getPrice();
			totalAmount = totalAmount + amount;
			
			//轉換BuyItem to OrderItem
			OrderItem orderItem = new OrderItem();
			orderItem.setProductId(buyItem.getProductId());
			orderItem.setQuantity(buyItem.getQuantity());
			orderItem.setAmount(amount);

			orderItemList.add(orderItem);
		}
		
		//創建訂單
		Integer orderId = orderDao.createOrder(userId, totalAmount);
		orderDao.createOrderItems(orderId, orderItemList);
		
		return orderId;
	}

	@Override
	public Order getOrderById(Integer orderId) {
		Order order = orderDao.getOrderById(orderId);
		
		List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);
		
		order.setOrderItemList(orderItemList);
		
		return order;
	}
	
}
