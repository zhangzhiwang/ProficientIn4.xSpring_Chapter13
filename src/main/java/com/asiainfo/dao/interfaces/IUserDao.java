package com.asiainfo.dao.interfaces;

import java.util.List;

import com.asiainfo.entity.User;

public interface IUserDao {
	void save (User user);
	User save2 (User user);
	void batchSave(List<User> userList);
	List<User> getUserByName(String name);
}
