package com.daniel.shoppingPlatform.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.daniel.shoppingPlatform.model.Product;

import dto.ProductQueryParams;
import dto.ProductRequest;
import rowmapper.ProductRowMapper;

@Component
public class ProductDaoImpl implements ProductDao {
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public Integer countProduct(ProductQueryParams productQueryParams) {
		
		String sql = "SELECT count(*) FROM mall.product WHERE 1=1";
		
		Map<String, Object> map = new HashMap<>();
		
		sql  = addFilteringSql(sql, map, productQueryParams);
//改為上方寫法		
//		if(productQueryParams.getCategory() != null) {
//			sql = sql + " AND category = :category";
//			map.put("category", productQueryParams.getCategory().name());
//			//map.put("category", category.name()); 未將刪選條件塞入ProductQueryParams中的取值寫法
//			//Enum類型呼叫name方法= 使enum變成string類型
//		}
//		if(productQueryParams.getSearch() != null) {
//			sql = sql + " AND product_name LIKE :search";
//			map.put("search", "%"+productQueryParams.getSearch()+"%");
//			//map.put("search", "%"+search+"%");
//			//加上%在前ex: 代表在資料庫中, "最後"有接上蘋果兩字的, 就是目標資料(％蘋果), 前後都加代表整個資料有蘋果就是目標資料
//		}
		
		Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
		
		return total;
	}

	@Override
	public List<Product> getProducts(ProductQueryParams productQueryParams) {
		
		// 1=1用途: 用於sql若有多重條件時, 比較好寫(check chatGPT)
		String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, "+
					"created_date, last_modified_date FROM mall.product WHERE 1=1";
		
		Map<String, Object> map = new HashMap<>();
		
		sql  = addFilteringSql(sql, map, productQueryParams);
//		if(productQueryParams.getCategory() != null) {
//			sql = sql + " AND category = :category";
//			map.put("category", productQueryParams.getCategory().name());
//			//map.put("category", category.name()); 未將刪選條件塞入ProductQueryParams中的取值寫法
//			//Enum類型呼叫name方法= 使enum變成string類型
//		}
//		if(productQueryParams.getSearch() != null) {
//			sql = sql + " AND product_name LIKE :search";
//			map.put("search", "%"+productQueryParams.getSearch()+"%");
//			//map.put("search", "%"+search+"%");
//			//加上%在前ex: 代表在資料庫中, "最後"有接上蘋果兩字的, 就是目標資料(％蘋果), 前後都加代表整個資料有蘋果就是目標資料
//		}
		//注意在下sql 指令, 前後都依定要" 加上空白鍵 "要不然很容易莫名抓不到錯誤!!!
		sql = sql+" ORDER BY "+productQueryParams.getOrderBy()+" "+productQueryParams.getSort();
		//在此處不用使用if判斷式是因為在controller層已有先使用defaultValue定義其本身定不會為null
		//注意: 此處取orderBy的方法不行用map.put" : orderBY "
		
		//分頁
		sql = sql+" LIMIT :limit OFFSET :offset";
		map.put("limit",productQueryParams.getLimit());
		map.put("offset",productQueryParams.getOffset());
		
		List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
		
		return productList;
	}

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

	@Override
	public void updateProduct(Integer productId, ProductRequest productRequest) {
		String sql = "UPDATE mall.product SET product_name = :productName,category = :category, image_url = :imageUrl, "+
					"price = :price, stock =:stock, description = :description, last_modified_date = :lastModifiedDate "+
					"WHERE product_id = :productId";

		Map<String, Object> map = new HashMap<>();
		map.put("productId",productId);
		map.put("productName",productRequest.getProductName());
		map.put("category",productRequest.getCategory().toString());
		//.toString() 是為了將Enum值轉換成String後, 將要update的資料再丟給sql( sql中category適用string宣告)
		map.put("imageUrl",productRequest.getImageUrl());
		map.put("price",productRequest.getPrice());
		map.put("stock",productRequest.getStock());
		map.put("description",productRequest.getDescription());
		
		map.put("lastModifiedDate",new java.util.Date());
		
		namedParameterJdbcTemplate.update(sql, map);
		
	}
	
	@Override
	public void updateStock(Integer productId, Integer stock) {
		String sql = "UPDATE product SET stock = :stock, last_modified_date = :lastModifiedDate "+
				"WHERE product_id = :productId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("productId", productId);
		map.put("stock", stock);
		map.put("lastModifiedDate", new Date());
		
		namedParameterJdbcTemplate.update(sql, map);
	}

	@Override
	public void deleteProductById(Integer productId) {
		
		String sql = "DELETE FROM mall.product WHERE product_id = :productId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("productId", productId);
		
		namedParameterJdbcTemplate.update(sql, map);
		
	}
	
	//此段方法原在上方countProduct()及getProduct()皆有重複使用,若之後要變動內容則要改兩個內容, 故直接將重複的內容
	//拉出來令寫一個方法, 再將此方法帶回; 此處用pivate原因為此方法只在此程式內使用
	private String addFilteringSql(String sql, Map<String, Object> map, ProductQueryParams productQueryParams) {
		
		if(productQueryParams.getCategory() != null) {
			sql = sql + " AND category = :category";
			map.put("category", productQueryParams.getCategory().name());
			//map.put("category", category.name()); 未將刪選條件塞入ProductQueryParams中的取值寫法
			//Enum類型呼叫name方法= 使enum變成string類型
		}
		if(productQueryParams.getSearch() != null) {
			sql = sql + " AND product_name LIKE :search";
			map.put("search", "%"+productQueryParams.getSearch()+"%");
			//map.put("search", "%"+search+"%");
			//加上%在前ex: 代表在資料庫中, "最後"有接上蘋果兩字的, 就是目標資料(％蘋果), 前後都加代表整個資料有蘋果就是目標資料
		}
		
		return sql;
	}

}
