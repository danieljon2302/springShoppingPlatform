package rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.daniel.shoppingPlatform.model.OrderItem;

public class OrderItemRowMapper implements RowMapper<OrderItem> {
	
	@Override
	public OrderItem  mapRow(ResultSet resultSet, int i) throws SQLException{
		//只要在DAO曾有SELECT到的欄位, 都可用resultSet取得該值, 不用管他是JOIN哪張table
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderItemId(resultSet.getInt("order_item_id"));
		orderItem.setOrderId(resultSet.getInt("order_id"));
		orderItem.setProductId(resultSet.getInt("product_id"));
		orderItem.setQuantity(resultSet.getInt("quantity"));
		orderItem.setAmount(resultSet.getInt("amount"));
		
		orderItem.setProductName(resultSet.getString("product_name"));
		orderItem.setImageUrl(resultSet.getString("image_url"));
		
		return orderItem;
		
	}
	
}
