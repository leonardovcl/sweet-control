package dev.leonardovcl.sweetcontrol.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.leonardovcl.sweetcontrol.model.RecipeIngredient;
import dev.leonardovcl.sweetcontrol.model.repository.IngredientRepository;
import dev.leonardovcl.sweetcontrol.model.repository.RecipeIngredientRepository;

@Controller
@RequestMapping("/recipe_ingredients")
public class RecipeIngredientController {

	@Autowired
	private RecipeIngredientRepository recipeIngredientRepository;
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
	@GetMapping("/edit/{idRecipeIngredient}")
	public String showRecipeIngredientUpdateForm(@PathVariable("idRecipeIngredient") Long idRecipeIngredient, Model model) {
		model.addAttribute("recipeIngredient", recipeIngredientRepository.findById(idRecipeIngredient).get());
		model.addAttribute("recipe", recipeIngredientRepository.findById(idRecipeIngredient).get().getRecipe());
		model.addAttribute("ingredientList", ingredientRepository.findAll());
		return "recipeIngredientUpdateForm";
	}

	@PostMapping("/edit/{idRecipeIngredient}")
	public String updateRecipeIngredient(@PathVariable("idRecipeIngredient") Long idRecipeIngredient, 
										@Valid RecipeIngredient recipeIngredient) {
		Long recipeId = recipeIngredientRepository.findById(idRecipeIngredient).get().getRecipe().getId();
		System.out.println(recipeIngredient.getRecipe());
		System.out.println(recipeIngredient.getIngredientEntry());
		recipeIngredientRepository.save(recipeIngredient);
		
		return "redirect:/recipes/" + Long.toString(recipeId);
	}
	
	@GetMapping("/delete/{idRecipeIngredient}")
	public String deleteRecipe(@PathVariable("idRecipeIngredient") Long idRecipeIngredient) {
		RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(idRecipeIngredient).get();
		String recipeId = Long.toString(recipeIngredient.getRecipe().getId());
		recipeIngredientRepository.deleteById(idRecipeIngredient);
		return "redirect:/recipes/" + recipeId;
	}
}
