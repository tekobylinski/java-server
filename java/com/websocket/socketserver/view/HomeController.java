package com.websocket.socketserver.view;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.websocket.socketserver.model.ServerLog;
import com.websocket.socketserver.model.User;
import com.websocket.socketserver.repository.UserRepository;
import com.websocket.socketserver.service.Logs;


@Controller
public class HomeController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("home")
	public ModelAndView homeView(Model model, HttpServletRequest req) {
		String id = (String) req.getSession().getAttribute("id");
		if (id != null) {
			User user = userRepository.findById(Integer.valueOf(id));
			model.addAttribute("role", "1");
			model.addAttribute("logs", Logs.serverLogList);
		}
		return new ModelAndView("home");
	}

	@PostMapping("check")
	public ModelAndView checkView(Model model, @RequestParam String number) {
		User user = userRepository.findByNumber(number);
		if (user != null) {
			Logs.serverLogList
					.add(new ServerLog((Logs.serverLogList.size() + 1),
							user.getFirstName() + " " + user.getLastName() + " o ID " + user
									.getNumber() + " odczytał kartę"));
		} else {
			Logs.serverLogList.add(new ServerLog((Logs.serverLogList.size() + 1),
					"Użytkownik o ID " + number + " nie istnieje"));
		}
		return new ModelAndView("home");
	}
}