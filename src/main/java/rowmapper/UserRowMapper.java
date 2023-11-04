package rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.daniel.shoppingPlatform.model.User;

public class UserRowMapper implements RowMapper<User> {
	
	public User mapRow(ResultSet resultSet, int i) throws SQLException {

		User user = new User();
		user.setUserId(resultSet.getInt("user_id"));
		user.setEmail(resultSet.getString("email"));
		user.setPassword(resultSet.getString("password"));
		user.setCreateDate(resultSet.getTime("created_date"));
		user.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));
		
		return user;
	}

	
}
