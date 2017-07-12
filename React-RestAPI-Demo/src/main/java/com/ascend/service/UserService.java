package com.ascend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ascend.dao.UserDAO;
import com.ascend.domain.User;

/**
 * User service.
 * 
 * @author anh.ngo
 *
 */
@Transactional
@Service("userService")
public class UserService {
 
    @Autowired
    private UserDAO dao;
 
    public List<User> list() {
        return dao.list();
    }
    
    public User findById(Integer id) {
        return dao.findById(id);
    }
 
    public Boolean saveUser(User user) {
        Boolean result = dao.save(user);
        return result;
    }
 
    public Boolean updateUser(User user) {
    	User entity = dao.findById(user.getUserId());
        if(entity != null){
            entity.setName(user.getName());
            entity.setAge(user.getAge());
            entity.setAddress(user.getAddress());
            dao.update(entity);
            return true;
        }else{
        	return false;
        }
    }
    
    public Boolean deleteById(Integer id) {
    	User entity = dao.findById(id);
        if(entity != null){
        	dao.delete(entity);
        	return true;
        }else{
        	return false;
        }    	
    }
}