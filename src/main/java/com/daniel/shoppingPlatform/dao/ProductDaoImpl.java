package com.daniel.shoppingPlatform.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.daniel.shoppingPlatform.model.Product;

import dto.ProductRequest;
import rowmapper.ProductRowMapper;

@Component
public class ProductDaoImpl implements ProductDao {
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Product getProductById(Integer productId) {
		
		String sql = "SELECT product_id, product_name, category, image_url, price, stock,"+
						" description, created_date, last_modified_date"+ 
						" FROM mall.product WHERE product_id = :productId";
		
		Map<String, Object> map =new HashMap<>();
		map.put("productId", productId);
		
		List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
		
//		最後一個參數為rowMapper: 將sql返回的資料轉為javaObject
		if(productList.size()>0) {
			return productList.get(0);
		}else {
			return null;
		}		
	}

	@Override
	public Integer createProduct(ProductRequest productRequest) {
		String sql = "INSERT INTO mall.product(product_name, category, image_url, price, stock, "+
					"description, created_date, last_modified_date)"+
					"VALUES (:productName, :category, :imageUrl, :price, :stock, :description, "+
					":createdDate, :lastModifiedDate)";
		
		Map<String, Object> map = new HashMap<>();
		map.put("productName", productRequest.getProductName());
		map.put("category", productRequest.getCategory().toString());
		map.put("imageUrl", productRequest.getImageUrl());
		map.put("price", productRequest.getPrice());
		map.put("stock", productRequest.getStock());
		map.put("description", productRequest.getDescription());
		
		java.util.Date now = new java.util.Date();
		map.put("createdDate", now);
		map.put("lastModifiedDate", now);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();

		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
		
		int productId = keyHolder.getKey().intValue();
		
		return productId;
	}

}
