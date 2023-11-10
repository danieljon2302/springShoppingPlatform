package com.daniel.shoppingPlatform.dao;

import java.util.List;

import com.daniel.shoppingPlatform.model.Product;

import dto.ProductQueryParams;
import dto.ProductRequest;

public interface ProductDao {
	
	Integer countProduct(ProductQueryParams productQueryParams);
	
	List<Product> getProducts(ProductQueryParams productQueryParams);
	
	Product getProductById(Integer productId);
	
	Integer createProduct(ProductRequest productRequest);
	
	void updateProduct(Integer productId, ProductRequest productRequest);
	
	void deleteProductById(Integer productId);
	
	void updateStock(Integer productId, Integer stock);
}
