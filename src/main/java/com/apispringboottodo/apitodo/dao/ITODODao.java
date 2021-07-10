package com.apispringboottodo.apitodo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.apispringboottodo.apitodo.entitiy.TODO;

@Repository
public interface ITODODao extends JpaRepository<TODO, Integer> {
	@Query("select t from TODO t where t.user.Id like ?1")
	public Page<TODO> findTodoByUser(Integer term, Pageable pageable);
}
