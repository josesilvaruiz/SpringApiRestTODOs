package com.apispringboottodo.apitodo.service;

import java.util.List;

import com.apispringboottodo.apitodo.entitiy.User;

public interface IUserService {
	
	public List<User> findAll();
	
	public User save(User user);
	
	public User findById(Integer id);
}
