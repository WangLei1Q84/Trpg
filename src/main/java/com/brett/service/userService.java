package com.brett.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brett.Entity.user;
import com.brett.Mapper.userMapper;

@Service
public class userService {

	@Autowired
	userMapper userMapper;
	
	@Transactional
	public int addUser(user user){
		int count=userMapper.addUser(user);
		int ct=userMapper.addUserRoles(user.getUsername());
		if(count==1&ct==1){
			return 1;
		}
		return 0;
	}
	
	public String findByName(String name){
		user user=userMapper.findByUserName(name);
		if(user==null){
			return "true";
		}
		return "false";
	}
}
