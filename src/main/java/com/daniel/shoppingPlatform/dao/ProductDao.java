package com.daniel.shoppingPlatform.dao;

import java.util.List;

import com.daniel.shoppingPlatform.model.Product;

import dto.ProductRequest;

public interface ProductDao {
	
	List<Product> getProducts();
	
	Product getProductById(Integer productId);
	
	Integer createProduct(ProductRequest productRequest);
	
	void updateProduct(Integer productId, ProductRequest productRequest);
	
	void deleteProductById(Integer productId);
}
