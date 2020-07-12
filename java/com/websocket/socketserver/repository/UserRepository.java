package com.websocket.socketserver.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.websocket.socketserver.model.User;

public interface UserRepository extends CrudRepository<User, String> {

	User findById(Integer id);

	List<User> findAll();

	User findByLoginAndPassword(String login, String password);

	User findByNumber(String number);

}
