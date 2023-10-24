package com.daniel.shoppingPlatform.dao;

import com.daniel.shoppingPlatform.model.Product;

import dto.ProductRequest;

public interface ProductDao {
	
	Product getProductById(Integer productId);
	
	Integer createProduct(ProductRequest productRequest);
	
}
