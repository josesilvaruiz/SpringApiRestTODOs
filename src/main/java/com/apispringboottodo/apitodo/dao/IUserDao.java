package com.apispringboottodo.apitodo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apispringboottodo.apitodo.entitiy.User;

@Repository
public interface IUserDao extends CrudRepository<User, Integer>{

	
}
