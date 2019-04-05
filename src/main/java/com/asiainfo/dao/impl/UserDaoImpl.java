package com.asiainfo.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;

import com.asiainfo.dao.interfaces.IUserDao;
import com.asiainfo.entity.Post;
import com.asiainfo.entity.User;

@Repository
public class UserDaoImpl implements IUserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private LobHandler lobHandler;
	@Autowired
	private DataFieldMaxValueIncrementer dataFieldMaxValueIncrementer;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private static final String SAVE_SQL = "insert into t_user (user_name,password,credits,last_visit,last_ip) values (?,?,?,?,?)";
	private static final String SAVE_SQL2 = "insert into t_user (user_id,user_name,password,credits,last_visit,last_ip) values (?,?,?,?,?,?)";
	private static final String SAVE_SQL3 = "insert into t_user (user_id,user_name,password,credits,last_visit,last_ip) values (:userId,:userName,:password,:credits,:lastVisit,:lastIp)";
	private static final String QUERY_SQL = "select * from t_user where user_name=?";
	private static final String QUERY_SQL_2 = "select * from t_user where user_id=?";
	
	public void save(final User user) {
		Object[] params = new Object[]{user.getUserName(), user.getPassword(), user.getCredits(), user.getLastVisit(), user.getLastIp()};
		
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
		
		// 用程序产生自增主键而不使用数据库的
		jdbcTemplate.update(SAVE_SQL, new Object[]{dataFieldMaxValueIncrementer.nextIntValue(), user.getUserName(), user.getPassword(), user.getCredits(), user.getLastVisit(), user.getLastIp()});
	}
	
	public User save2 (final User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement preparedStatement = con.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS);
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
		jdbcTemplate.batchUpdate(SAVE_SQL2, new BatchPreparedStatementSetter() {
			
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
	
	public void saveUserWithNamedParam(User user) {
		namedParameterJdbcTemplate.update(SAVE_SQL3, new BeanPropertySqlParameterSource(user));
	}
	
	public void saveUserWithNamedParam2(User user) {
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("userName", user.getUserName()).addValue("password", user.getPassword()).addValue("userId", user.getUserId()).addValue("credits", user.getCredits()).addValue("lastVisit", user.getLastVisit()).addValue("lastIp", user.getLastIp());
		namedParameterJdbcTemplate.update(SAVE_SQL3, sqlParameterSource);
	}

	public List<User> getUserByName(String name) {
		final List<User> userList = new ArrayList<User>();
		jdbcTemplate.query(QUERY_SQL, new Object[]{name}, new int[]{Types.VARCHAR}, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user.setCredits(rs.getInt("credits"));
				user.setLastVisit(rs.getTimestamp("last_visit"));
				user.setLastIp(rs.getString("last_ip"));
				userList.add(user);
			}});
		
		return userList;
	}
	
	public List<User> getUserByName2(String name) {
		return jdbcTemplate.query(QUERY_SQL, new Object[]{name}, new int[]{Types.VARCHAR}, new RowMapper<User>() {

			public User mapRow(ResultSet rs, int arg1) throws SQLException {
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user.setCredits(rs.getInt("credits"));
				user.setLastVisit(rs.getTimestamp("last_visit"));
				user.setLastIp(rs.getString("last_ip"));
				return user;
			}
		});
	}
	
	public User getUserById(int id) {
		final User user = new User();
		jdbcTemplate.query(QUERY_SQL_2, new Object[]{id}, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException {
				user.setUserId(rs.getInt("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user.setCredits(rs.getInt("credits"));
				user.setLastVisit(rs.getTimestamp("last_visit"));
				user.setLastIp(rs.getString("last_ip"));
			}});
		
		return user;
	}
	
	public List<User> getUserById2(int id) {
		return jdbcTemplate.query(QUERY_SQL_2, new Object[]{id}, new RowMapper<User>() {

			public User mapRow(ResultSet rs, int arg1) throws SQLException {
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user.setCredits(rs.getInt("credits"));
				user.setLastVisit(rs.getTimestamp("last_visit"));
				user.setLastIp(rs.getString("last_ip"));
				return user;
			}});
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int getCreditsById(final int id) {
		String sql = "{call procedure_test(?,?)}";
		return jdbcTemplate.execute(sql, new CallableStatementCallback() {

			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				cs.setInt(1, id);
				cs.registerOutParameter(2, Types.INTEGER);
				cs.execute();
				return cs.getInt(2);
			}
		});
	}
	
	public void addPost(final Post post) {
		String sql = "insert into t_post (post_id,post_text,post_attach) values (?,?,?)";
		jdbcTemplate.execute(sql, new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
			
			@Override
			protected void setValues(PreparedStatement ps, LobCreator lc) throws SQLException, DataAccessException {
				ps.setInt(1, post.getPostId());
				lc.setClobAsString(ps, 2, post.getPostText());
				lc.setBlobAsBytes(ps, 3, post.getPostAttach());
			}
		});
	}
	
	public List<Post> getAttatchById(int id) {
		String sql = "select * from t_post where post_id=?";
		return jdbcTemplate.query(sql, new Object[] { id }, new RowMapper<Post>() {

			public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
				Post post = new Post();
				post.setPostId(rs.getInt("post_id"));

				byte[] bytes = lobHandler.getBlobAsBytes(rs, "post_attach");

				post.setPostAttach(bytes);

				return post;
			}
		});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getAttatchById2(int id, final OutputStream os) {
		String sql = "select * from t_post where post_id=?";
		jdbcTemplate.query(sql, new Object[] { id }, new AbstractLobStreamingResultSetExtractor() {

			@Override
			protected void streamData(ResultSet rs) throws SQLException, IOException, DataAccessException {
				InputStream is = lobHandler.getBlobAsBinaryStream(rs, "post_attach");
				if(is != null) {
					FileCopyUtils.copy(is, os);
				}
			}
		});
	}
	
	public void savePost(Post post) {
		String sql = "insert into t_post (post_id,topic_id,forum_id,user_id) values (?,?,?,?)";
		jdbcTemplate.update(sql, new Object[]{dataFieldMaxValueIncrementer.nextIntValue(), post.getTopicId(), post.getForumId(), post.getUserId()});
	}
}
