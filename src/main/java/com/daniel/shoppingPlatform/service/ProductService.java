package com.daniel.shoppingPlatform.service;

import com.daniel.shoppingPlatform.model.Product;

import dto.ProductRequest;

public interface ProductService {
	
	Product getProductById(Integer productId);
	
	Integer createProduct(ProductRequest productRequest);
	
}
