package com.buzzfactory.lotto.dao;

import java.util.List;

import com.buzzfactory.lotto.entity.Entity;


public interface Dao<T extends Entity, I> {

	List<T> findAll();


	T find(I id);


	T save(T entity);


	void delete(I id);

}
