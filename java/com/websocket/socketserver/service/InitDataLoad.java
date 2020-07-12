package com.websocket.socketserver.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.websocket.socketserver.model.User;
import com.websocket.socketserver.repository.UserRepository;

@Service
public class InitDataLoad {

	@Autowired
	private UserRepository userRepository;


	@PostConstruct
	public void init() {

		User user1 = new User(1, "Jan", "Kowalski", "test", "test", "552163548304", "true");
		userRepository.save(user1);

		User user2 = new User(2, "Adam", "Kowalski", "test2","test2", "438729964014", "false");
		userRepository.save(user2);
	}
}