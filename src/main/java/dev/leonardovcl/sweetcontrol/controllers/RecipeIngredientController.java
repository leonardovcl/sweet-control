package dev.leonardovcl.sweetcontrol.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.leonardovcl.sweetcontrol.model.RecipeIngredient;
import dev.leonardovcl.sweetcontrol.model.SecurityUser;
import dev.leonardovcl.sweetcontrol.model.repository.RecipeIngredientRepository;
import dev.leonardovcl.sweetcontrol.model.repository.UserRepository;

@Controller
@RequestMapping("/recipe_ingredients")
public class RecipeIngredientController {

	@Autowired
	private RecipeIngredientRepository recipeIngredientRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/edit/{idRecipeIngredient}")
	public String showRecipeIngredientUpdateForm(@PathVariable("idRecipeIngredient") Long idRecipeIngredient,
												@AuthenticationPrincipal SecurityUser securityUser,
												Model model) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(idRecipeIngredient).get();
		
		if(recipeIngredient.getRecipe().getRecipeOwner().getId() != userId) {
			return "error/accessDenied";
		}
		
		model.addAttribute("recipeIngredient", recipeIngredient);
		
		return "recipes/recipeingredients/recipeIngredientUpdateForm";
	}

	@PostMapping("/edit/{idRecipeIngredient}")
	public String updateRecipeIngredient(@PathVariable("idRecipeIngredient") Long idRecipeIngredient, 
										@Valid RecipeIngredient recipeIngredient,
										BindingResult bindingResult,
										@AuthenticationPrincipal SecurityUser securityUser,
										Model model) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		
		if(recipeIngredient.getRecipe().getRecipeOwner().getId() != userId) {
			return "error/accessDenied";
		} else if(bindingResult.hasErrors()) {
			model.addAttribute("recipeIngredient", recipeIngredient);
			
			return "recipes/recipeingredients/recipeIngredientUpdateForm";
		}
		
		Long recipeId = recipeIngredientRepository.findById(idRecipeIngredient).get().getRecipe().getId();
		
		recipeIngredientRepository.save(recipeIngredient);
		
		return "redirect:/recipes/" + recipeId;
	}
	
	@GetMapping("/delete/{idRecipeIngredient}")
	public String deleteRecipe(@PathVariable("idRecipeIngredient") Long idRecipeIngredient,
								@AuthenticationPrincipal SecurityUser securityUser) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(idRecipeIngredient).get();
		
		if(recipeIngredient.getRecipe().getRecipeOwner().getId() != userId) {
			return "error/accessDenied";
		}
		
		String recipeId = Long.toString(recipeIngredient.getRecipe().getId());
		
		recipeIngredientRepository.deleteById(idRecipeIngredient);
		
		return "redirect:/recipes/" + recipeId;
	}
}
