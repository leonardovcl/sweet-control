package dev.leonardovcl.sweetcontrol.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
		model.addAttribute("user", new User());
		return "/users/userForm";
	}
	
	@PostMapping("/registration")
	public String registerUser(
			@Valid User user,
			BindingResult bindingResult,
			Model model) {

		if(userRepository.findByUsername(user.getUsername()).isPresent()) {
			
			model.addAttribute("user", user);
			model.addAttribute("usernameUniqueConstrainError", true);
			
			return "/users/userForm";
			
		} else if(bindingResult.hasErrors()) {
			
			model.addAttribute("user", user);
			return "/users/userForm";
			
		} else {
			
			User newUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()));
			userRepository.save(newUser);
			
			return "redirect:/login";
			
		}
	}
	
}
