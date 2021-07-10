package com.apispringboottodo.apitodo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apispringboottodo.apitodo.dao.ITODODao;
import com.apispringboottodo.apitodo.entitiy.TODO;

@Service
public class TODOServiceImpl implements ITODOService {

	@Autowired
	private ITODODao TODODao;
	
	@Override
	@Transactional(readOnly = true)
	public List<TODO> findAll () {
		return (List<TODO>) TODODao.findAll();
	}
	
	@Override
	public Page<TODO> findAll(Pageable pageable) {
		return TODODao.findAll(pageable);
	}
	

	@Override
	public TODO findById(Integer id) {
		return TODODao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public TODO save(TODO todo) {
		return TODODao.save(todo);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		TODODao.deleteById(id);
	}

	@Override
	public Page<TODO> findTodoByUser(Integer term, Pageable pageable) {
		return TODODao.findTodoByUser(term, pageable);
	}



}
