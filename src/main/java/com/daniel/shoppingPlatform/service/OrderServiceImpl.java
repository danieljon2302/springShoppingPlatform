package com.daniel.shoppingPlatform.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.daniel.shoppingPlatform.dao.OrderDao;
import com.daniel.shoppingPlatform.dao.ProductDao;
import com.daniel.shoppingPlatform.dao.UserDao;
import com.daniel.shoppingPlatform.model.Order;
import com.daniel.shoppingPlatform.model.OrderItem;
import com.daniel.shoppingPlatform.model.Product;
import com.daniel.shoppingPlatform.model.User;

import dto.BuyItem;
import dto.CreateOrderRequest;

@Component
public class OrderServiceImpl implements OrderService {

	private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private UserDao userDao;

	//注意: 在操作多張table時(createOrder, createOrderItem), 一定要加上Transational的註解, 
	//萬一噴出exception, 則可以復原( 確保兩個table是同時發生/ 不發生)
	@Transactional
	@Override
	public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
		//檢測userId是否存在
		User user = userDao.getUserById(userId);
		if(user == null) {
			log.warn("該 userId {} 不存在", userId);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		//已從前端獲得productId與quantity, 現需自己產生一func計算總額amount(order table)
		int totalAmount = 0;
		List<OrderItem> orderItemList = new ArrayList<>();
		
		for(BuyItem buyItem : createOrderRequest.getBuyItemList()) {
			Product product = productDao.getProductById(buyItem.getProductId());
			
			//檢查該product庫存是否存在/ 足夠
			if (product == null) {
				log.warn("商品 {} 不存在, buyItem.getQuantity()");
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
				
			}else if (product.getStock() < buyItem.getQuantity()) {
				log.warn("商品 {} 庫存數量不足, 無法購買, 剩餘庫存 {}, 欲購買數量{}", 
						buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
			
			//扣商品庫存
			productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());
			
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
