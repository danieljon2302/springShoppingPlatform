package com.daniel.shoppingPlatform.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.daniel.shoppingPlatform.model.Product;

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

}
