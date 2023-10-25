package com.daniel.shoppingPlatform.service;

import java.util.List;

import com.daniel.shoppingPlatform.model.Product;

import dto.ProductRequest;

public interface ProductService {
	
	List<Product> getProducts();
	
	Product getProductById(Integer productId);
	
	Integer createProduct(ProductRequest productRequest);
	
	void updateProduct(Integer productId, ProductRequest productRequest);
	
	void deleteProductById(Integer productId);
}
