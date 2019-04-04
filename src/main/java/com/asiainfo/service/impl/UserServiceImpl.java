package com.asiainfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asiainfo.dao.interfaces.IUserDao;
import com.asiainfo.entity.User;
import com.asiainfo.service.interfaces.IUserService;

@Service
@Transactional
public class UserServiceImpl {
	@Autowired
	private IUserDao userDao;

	public void save(User user) {
		userDao.save(user);
	}
	
	public User save2(User user) {
		return userDao.save2(user);
	}
	
	public void batchSave(List<User> userList) {
		userDao.batchSave(userList);
	}
}
