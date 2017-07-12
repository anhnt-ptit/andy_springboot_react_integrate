package com.ascend.dao;

import org.springframework.stereotype.Repository;

import com.ascend.domain.User;

/**
 * User DAO.
 * 
 * @author anh.ngo
 *
 */
@Repository("userDAO")
public class UserDAO extends AbstractDao<Integer, User> {
 
    public User findById(Integer id) {
    	User employee = getByKey(id);
        return employee;
    }
 
    public Boolean save(User user) {
		Boolean result = persist(user);
		return result;
    }
}