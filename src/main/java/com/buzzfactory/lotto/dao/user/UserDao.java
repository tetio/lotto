package com.buzzfactory.lotto.dao.user;

import com.buzzfactory.lotto.dao.Dao;
import com.buzzfactory.lotto.entity.User;

import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserDao extends Dao<User, Long>, UserDetailsService {

	User findByName(String name);

}
