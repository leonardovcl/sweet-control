package dev.leonardovcl.sweetcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.leonardovcl.sweetcontrol.model.User;
import dev.leonardovcl.sweetcontrol.model.repository.UserRepository;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@GetMapping("/registration")
	public String showUserForm(Model model) {
		model.addAttribute("usernameError", false);
		return "/users/userForm";
	}
	
	@PostMapping("/registration")
	public String registerUser(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			Model model
	) {
		
		if(userRepository.findByUsername(username).isPresent()) {
			model.addAttribute("usernameError", true);
			model.addAttribute("username", username);
			return "/users/userForm";
		}
		
		User newUser = new User(username, passwordEncoder.encode(password));
		userRepository.save(newUser);
		
		return "redirect:/login";
	}
	
}
