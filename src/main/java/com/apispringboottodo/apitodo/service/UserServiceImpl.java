package com.apispringboottodo.apitodo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apispringboottodo.apitodo.dao.IUserDao;
import com.apispringboottodo.apitodo.entitiy.User;
@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserDao userDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<User> findAll () {
		return (List<User>) userDao.findAll();
	}

	@Override
	@Transactional
	public User save(User user) {
		return userDao.save(user);
	}

	@Transactional(readOnly = true)
	public User findById(Integer id) {
		return userDao.findById(id).orElse(null);
	}

}
