package rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.daniel.shoppingPlatform.model.Product;

public class ProductRowMapper implements RowMapper<Product> {
	//使用jdbc的rowmapper
	//<將返回的值轉為product object>
	
	@Override
	public Product mapRow(ResultSet rs, int i) throws SQLException {
		Product product = new Product();

		product.setProductId(rs.getInt("product_id"));
		product.setProductName(rs.getString("product_name"));
//		rs.getString/getInt 是看要取得欄位得類型是什麼類型
		product.setCategory(rs.getString("category"));
		product.setImageUrl(rs.getString("image_url"));
		product.setPrice(rs.getInt("price"));
		product.setStock(rs.getInt("stock"));
		product.setDescription(rs.getString("description"));
		product.setCreatedDate(rs.getTimestamp("created_date"));
		product.setLastModifiedDate(rs.getTimestamp("last_modified_date"));

		
		return product;
	}

}
