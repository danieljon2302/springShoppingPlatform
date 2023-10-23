package com.daniel.shoppingPlatform.dao;

import com.daniel.shoppingPlatform.model.Product;

public interface ProductDao {
	
	Product getProductById(Integer productId);
	
}
