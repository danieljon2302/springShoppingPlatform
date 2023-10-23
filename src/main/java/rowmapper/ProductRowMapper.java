package rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.daniel.shoppingPlatform.constant.ProductCategory;
import com.daniel.shoppingPlatform.model.Product;

public class ProductRowMapper implements RowMapper<Product> {
	//使用jdbc的rowmapper
	//<將返回的值轉為product object>
	
	@Override
	public Product mapRow(ResultSet rs, int i) throws SQLException {
		Product product = new Product();

		product.setProductId(rs.getInt("product_id"));
		//rs.getString/getInt 是看要取得欄位得類型是什麼類型
		product.setProductName(rs.getString("product_name"));
		//將sql得到的String字串轉為ProductCategory Enum
		//先用String categoryStr接住rs.getString("category");,
		//再用valueOf轉成ProductCategory category  (valueOf常用！String轉Enum)		
		String categoryStr = rs.getString("category");
		ProductCategory category = ProductCategory.valueOf(categoryStr);
		product.setCategory(category);
		//一行解決:product.setCategory(ProductCategory.valueOf(rs.getString("category")));
		
		product.setImageUrl(rs.getString("image_url"));
		product.setPrice(rs.getInt("price"));
		product.setStock(rs.getInt("stock"));
		product.setDescription(rs.getString("description"));
		product.setCreatedDate(rs.getTimestamp("created_date"));
		product.setLastModifiedDate(rs.getTimestamp("last_modified_date"));

		
		return product;
	}

}
