package dev.leonardovcl.sweetcontrol.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.leonardovcl.sweetcontrol.model.RecipeIngredient;
import dev.leonardovcl.sweetcontrol.model.repository.RecipeIngredientRepository;

@Controller
@RequestMapping("/recipe_ingredients")
public class RecipeIngredientController {

	@Autowired
	private RecipeIngredientRepository recipeIngredientRepository;
	
	@GetMapping("/edit/{idRecipeIngredient}")
	public String showRecipeIngredientUpdateForm(@PathVariable("idRecipeIngredient") Long idRecipeIngredient, Model model) {
		model.addAttribute("recipeIngredient", recipeIngredientRepository.findById(idRecipeIngredient).get());
		return "recipes/recipeingredients/recipeIngredientUpdateForm";
	}

	@PostMapping("/edit/{idRecipeIngredient}")
	public String updateRecipeIngredient(@PathVariable("idRecipeIngredient") Long idRecipeIngredient, 
										@Valid RecipeIngredient recipeIngredient,
										BindingResult bindingResult,
										Model model) {
		
		if(bindingResult.hasErrors()) {
			System.out.println(recipeIngredient.getIngredientEntry());
			model.addAttribute("recipeIngredient", recipeIngredient);
			
			return "recipes/recipeingredients/recipeIngredientUpdateForm";
		}
		
		Long recipeId = recipeIngredientRepository.findById(idRecipeIngredient).get().getRecipe().getId();
		recipeIngredientRepository.save(recipeIngredient);
		
		return "redirect:/recipes/" + recipeId;
	}
	
	@GetMapping("/delete/{idRecipeIngredient}")
	public String deleteRecipe(@PathVariable("idRecipeIngredient") Long idRecipeIngredient) {
		RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(idRecipeIngredient).get();
		String recipeId = Long.toString(recipeIngredient.getRecipe().getId());
		recipeIngredientRepository.deleteById(idRecipeIngredient);
		return "redirect:/recipes/" + recipeId;
	}
}
