package dev.leonardovcl.sweetcontrol.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.leonardovcl.sweetcontrol.model.Ingredient;
import dev.leonardovcl.sweetcontrol.model.SecurityUser;
import dev.leonardovcl.sweetcontrol.model.User;
import dev.leonardovcl.sweetcontrol.model.repository.IngredientRepository;
import dev.leonardovcl.sweetcontrol.model.repository.UserRepository;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

	@Autowired
	private IngredientRepository ingredientRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public String showIngredients(
			@RequestParam(value = "page", required = false,  defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "5") int size,
			@RequestParam(value = "nameLike", required = false, defaultValue = "") String nameLike,
			@AuthenticationPrincipal SecurityUser securityUser,
			Model model) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
		Page<Ingredient> ingredientList;
		
		if (!nameLike.isBlank()) {
			ingredientList = ingredientRepository.findByNameContainingIgnoreCaseAndIngredientOwnerId(nameLike, userId, pageable);
		} else {
			ingredientList = ingredientRepository.findByIngredientOwnerId(userId, pageable);
		}
		
		model.addAttribute("ingredientList", ingredientList);
		
		model.addAttribute("nameLike", nameLike);
		
		return "/ingredients/ingredients";
	}
	
	@GetMapping("/register")
	public String showIngredientRegisterForm(Model model) {
		model.addAttribute("ingredient", new Ingredient());
		return "/ingredients/ingredientForm";
	}
	
	@PostMapping("/register")
	public String registerIngredient(@Valid Ingredient ingredient,
									BindingResult bindingResult,
									@AuthenticationPrincipal SecurityUser securityUser,
									Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("ingredient", ingredient);
			return "/ingredients/ingredientForm";
		}
		
		User user = userRepository.findByUsername(securityUser.getUsername()).get();
		ingredient.setIngredientOwner(user);
		
		ingredientRepository.save(ingredient);
		
		return "redirect:/ingredients";
	}
	
	@GetMapping("/edit/{idIngredient}")
	public String showIngredientUpdateForm(@PathVariable("idIngredient") Long idIngredient,
											@AuthenticationPrincipal SecurityUser securityUser,								
											Model model) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		Ingredient ingredient = ingredientRepository.findById(idIngredient).get();
		
		if(ingredient.getIngredientOwner().getId() != userId) {
			return "error/accessDenied";
		}
		
		model.addAttribute("ingredient", ingredient);
		
		return "/ingredients/ingredientUpdateForm";
	}
	
	@PostMapping("/edit/{idIngredient}")
	public String updateIngredient(@PathVariable("idIngredient") Long idIngredient,
									@Valid Ingredient ingredient,
									BindingResult bindingResult,
									@AuthenticationPrincipal SecurityUser securityUser,
									Model model) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		
		if(ingredient.getIngredientOwner().getId() != userId) {
			return "error/accessDenied";
		}
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("ingredient", ingredient);
			return "/ingredients/ingredientUpdateForm";
		}
		
		ingredientRepository.save(ingredient);
		return "redirect:/ingredients";
	}
	
	@GetMapping("/delete/{idIngredient}")
	public String deleteIngredient(@PathVariable("idIngredient") Long idIngredient,
									@AuthenticationPrincipal SecurityUser securityUser) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		Ingredient ingredient = ingredientRepository.findById(idIngredient).get();
		
		if(ingredient.getIngredientOwner().getId() != userId) {
			return "error/accessDenied";
		}
		
		ingredientRepository.deleteById(idIngredient);
		
		return "redirect:/ingredients";
	}
	
}
