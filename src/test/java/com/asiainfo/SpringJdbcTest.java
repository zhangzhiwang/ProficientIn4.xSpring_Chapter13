package com.asiainfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import com.asiainfo.dao.impl.UserDaoImpl;
import com.asiainfo.entity.Post;
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
	public void test() throws IOException {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//		UserServiceImpl userServiceImpl = (UserServiceImpl) applicationContext.getBean("userServiceImpl");
		
//		List<User> userList = new ArrayList<User>();
//		User user = new User();
//		user.setUserId(10);
//		user.setUserName("a");
//		user.setPassword("1234");
//		user.setCredits(10);
//		user.setLastVisit(new Timestamp(new Date().getTime()));
//		user.setLastIp("127.0.0.1");
//		userList.add(user);
//		
//		user = new User();
//		user.setUserId(11);
//		user.setUserName("a");
//		user.setPassword("1234");
//		user.setCredits(10);
//		user.setLastVisit(new Timestamp(new Date().getTime()));
//		user.setLastIp("127.0.0.1");
//		userList.add(user);
//		
//		userServiceImpl.batchSave(userList);
		
		
//		user = userServiceImpl.save2(user);
//		System.out.println(user);
		
		
		UserDaoImpl userDaoImpl = (UserDaoImpl) applicationContext.getBean("userDaoImpl");
//		List<User> userList = userDaoImpl.getUserByName2("a");
//		System.out.println(userList);
//		List<User> users = userDaoImpl.getUserById2(1);
//		System.out.println(users);
//		int credits = userDaoImpl.getCreditsById(1);
//		System.out.println(credits);
		
//		Post post = new Post();
//		post.setPostId(1);
//		post.setPostText("test");
//		
//		ClassPathResource classPathResource = new ClassPathResource("1537769758035.jpg");
//		byte[] picBytes = FileCopyUtils.copyToByteArray(classPathResource.getFile());
//		post.setPostAttach(picBytes);
//		
//		userDaoImpl.addPost(post);
		
//		userDaoImpl.getAttatchById2(1, new FileOutputStream(new File("/Users/zhangzhiwang/Desktop/test.jpg")));
		
//		Post post = new Post();
//		post.setForumId(1);
//		post.setUserId(2);
//		post.setTopicId(3);
//		for(int i = 1; i <= 6; i++) {
//			userDaoImpl.savePost(post);
//		}
		
		User user = new User();
		user.setUserName("c");
		user.setPassword("123321");
		user.setCredits(10);
		user.setLastVisit(new Timestamp(new Date().getTime()));
		user.setLastIp("127.0.0.1");
		userDaoImpl.saveUserWithNamedParam2(user);
	}
}
