package com.daniel.shoppingPlatform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.daniel.shoppingPlatform.dao.ProductDao;
import com.daniel.shoppingPlatform.model.Product;

import dto.ProductRequest;
@Component
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Override
	public List<Product> getProducts() {
		
		return productDao.getProducts();
	}
	
	@Override
	public Product getProductById(Integer productId) {
		
		return productDao.getProductById(productId);
	}

	@Override
	public Integer createProduct(ProductRequest productRequest) {
		
		return productDao.createProduct(productRequest);
	}

	@Override
	public void updateProduct(Integer productId, ProductRequest productRequest) {
		
		productDao.updateProduct(productId, productRequest);
		
	}

	@Override
	public void deleteProductById(Integer productId) {
		
		productDao.deleteProductById(productId);
		
	}

}
