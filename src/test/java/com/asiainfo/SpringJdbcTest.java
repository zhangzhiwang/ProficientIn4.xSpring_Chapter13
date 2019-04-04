package com.asiainfo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.asiainfo.entity.User;
import com.asiainfo.service.impl.UserServiceImpl;

/**
 * 使用spring JDBC持久化技术访问数据库
 *
 * @author zhangzhiwang
 * @date Apr 4, 2019 12:51:47 PM
 */
public class SpringJdbcTest {
	@Test
	public void test() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		UserServiceImpl userServiceImpl = (UserServiceImpl) applicationContext.getBean("userServiceImpl");
		
		List<User> userList = new ArrayList<User>();
		User user = new User();
		user.setUserId(10);
		user.setUserName("a");
		user.setPassword("1234");
		user.setCredits(10);
		user.setLastVisit(new Timestamp(new Date().getTime()));
		user.setLastIp("127.0.0.1");
		userList.add(user);
		
		user = new User();
		user.setUserId(11);
		user.setUserName("a");
		user.setPassword("1234");
		user.setCredits(10);
		user.setLastVisit(new Timestamp(new Date().getTime()));
		user.setLastIp("127.0.0.1");
		userList.add(user);
		
		userServiceImpl.batchSave(userList);
		
		
//		user = userServiceImpl.save2(user);
//		System.out.println(user);
	}
}
