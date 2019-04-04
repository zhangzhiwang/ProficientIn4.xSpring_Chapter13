package com.asiainfo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.asiainfo.dao.interfaces.IUserDao;
import com.asiainfo.entity.User;

@Repository
public class UserDaoImpl implements IUserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String SQL = "insert into t_user (user_name,password,credits,last_visit,last_ip) values (?,?,?,?,?)";
	private static final String SQL2 = "insert into t_user (user_id,user_name,password,credits,last_visit,last_ip) values (?,?,?,?,?,?)";
	
	public void save(final User user) {
		Object[] params = new Object[]{user.getUserId(), user.getUserName(), user.getPassword(), user.getCredits(), user.getLastVisit(), user.getLastIp()};
		
//		jdbcTemplate.update(SQL, params);
//		jdbcTemplate.update(SQL, user.getUserId(), user.getUserName(), user.getPassword(), user.getCredits(), user.getLastVisit(), user.getLastIp());
//		jdbcTemplate.update(SQL, params, new int[]{Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP, Types.VARCHAR});
//		jdbcTemplate.update(SQL, new PreparedStatementSetter() {
//
//			public void setValues(PreparedStatement preparedStatement) throws SQLException {
//				preparedStatement.setInt(1, user.getUserId());
//				preparedStatement.setString(2, user.getUserName());
//				preparedStatement.setString(3, user.getPassword());
//				preparedStatement.setInt(4, user.getCredits());
//				preparedStatement.setTimestamp(5, user.getLastVisit());
//				preparedStatement.setString(6, user.getLastIp());
//			}});
//		jdbcTemplate.update(new PreparedStatementCreator() {
//			
//			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//				PreparedStatement preparedStatement = con.prepareStatement(SQL);
//				preparedStatement.setInt(1, user.getUserId());
//				preparedStatement.setString(2, user.getUserName());
//				preparedStatement.setString(3, user.getPassword());
//				preparedStatement.setInt(4, user.getCredits());
//				preparedStatement.setTimestamp(5, user.getLastVisit());
//				preparedStatement.setString(6, user.getLastIp());
//				return preparedStatement;
//			}
//		});
	}
	
	public User save2 (final User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement preparedStatement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, user.getUserName());
				preparedStatement.setString(2, user.getPassword());
				preparedStatement.setInt(3, user.getCredits());
				preparedStatement.setTimestamp(4, user.getLastVisit());
				preparedStatement.setString(5, user.getLastIp());
				return preparedStatement;
			}
		}, keyHolder);
		
		user.setUserId(keyHolder.getKey().intValue());
		return user;
	}
	
	public void batchSave(final List<User> userList) {
		jdbcTemplate.batchUpdate(SQL2, new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
				User user = userList.get(i);
				preparedStatement.setInt(1, user.getUserId());
				preparedStatement.setString(2, user.getUserName());
				preparedStatement.setString(3, user.getPassword());
				preparedStatement.setInt(4, user.getCredits());
				preparedStatement.setTimestamp(5, user.getLastVisit());
				preparedStatement.setString(6, user.getLastIp());
			}
			
			public int getBatchSize() {
				return userList.size();
			}
		});
	}

	public List<User> getUserByName(String name) {
		return null;
	}

}
