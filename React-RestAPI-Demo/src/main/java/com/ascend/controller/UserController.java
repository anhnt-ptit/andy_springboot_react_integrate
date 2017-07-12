package com.ascend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ascend.domain.User;
import com.ascend.service.UserService;

/**
 * User RESTful controller for CRUD operations.
 * 
 * @author anh.ngo
 *
 */
@RestController
public class UserController {

	@Autowired
    private UserService service;

	@GetMapping("/users")
    public ResponseEntity<List<User>> users() {
		List<User> Users = this.service.list();
    	return new ResponseEntity<List<User>>(Users, HttpStatus.OK);
    }
    
    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
    	User User = this.service.findById(id);
    	if(User == null){
    		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    	}else{
    		return new ResponseEntity<User>(User, HttpStatus.OK);
    	}
    }
    
    @PostMapping(value = "/users")
	public ResponseEntity<User> addUser(@RequestBody User user) {
    	System.out.println("User insert request : "+user.toString());
    	//TODO: check result
    	this.service.saveUser(user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
    
    @PutMapping(value = "/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
    	//TODO: check result  
    	System.out.println("userId update request : "+id);
    	System.out.println("user update request : "+user.toString());
    	this.service.updateUser(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
    	//TODO: check result	
		System.out.println("UserId delete request : "+id);
		this.service.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}