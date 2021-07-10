package com.apispringboottodo.apitodo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.apispringboottodo.apitodo.entitiy.TODO;

public interface ITODOService {

	public List<TODO> findAll();
	
	public Page<TODO> findAll(Pageable pageable);
	
	public TODO findById(Integer id);
	
	public TODO save(TODO todo);
	
	public void delete(Integer id);

	public Page<TODO> findTodoByUser(Integer term, Pageable pageable);
}
