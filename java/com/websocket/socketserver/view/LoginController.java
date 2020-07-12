package com.websocket.socketserver.view;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.websocket.socketserver.model.User;
import com.websocket.socketserver.repository.UserRepository;

@Controller
public class LoginController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("login")
	public ModelAndView loginView() {
		return new ModelAndView("login");
	}

	@PostMapping("login")
	public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
		User user = userRepository.findByLoginAndPassword(username, password);
		if (user != null && user.getPassword().equals(password)) {
			request.getSession().setAttribute("id", Integer.toString(user.getId()));
			return "redirect:home";
		} else {
			return "login";
		}
	}
}
