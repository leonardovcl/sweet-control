package dev.leonardovcl.sweetcontrol.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.leonardovcl.sweetcontrol.model.Ingredient;
import dev.leonardovcl.sweetcontrol.model.Inventory.AmountType;
import dev.leonardovcl.sweetcontrol.model.Recipe;
import dev.leonardovcl.sweetcontrol.model.RecipeIngredient;
import dev.leonardovcl.sweetcontrol.model.repository.IngredientRepository;
import dev.leonardovcl.sweetcontrol.model.repository.RecipeIngredientRepository;
import dev.leonardovcl.sweetcontrol.model.repository.RecipeRepository;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
	@Autowired
	private RecipeIngredientRepository recipeIngredientRepository;
	
	@GetMapping
	public String showRecipes(Model model) {
		model.addAttribute("recipeList", recipeRepository.findAll());
		return "recipes";
	}
	
	@PostMapping
	public String showRecipesWithFilter(@RequestParam("nameLike") String nameLike, Model model) {
		model.addAttribute("recipeList", recipeRepository.findByNameContainingIgnoreCase(nameLike));
		return "recipes";
	}
	
	@GetMapping("/register")
	public String showRecipeRegisterForm(Model model) {
		model.addAttribute("recipe", new Recipe());
		return "recipeForm";
	}
	
	@PostMapping("/register")
	public String registerRecipe(@Valid Recipe recipe) {
		recipeRepository.save(recipe);
		return "redirect:/recipes";
	}
	
	@GetMapping("/{idRecipe}")
	public String showRecipeIngredientForm(@PathVariable("idRecipe") Long idRecipe, Model model) {
		model.addAttribute("recipeIngredientList", recipeIngredientRepository.findByRecipeId(idRecipe));
		model.addAttribute("recipe", recipeRepository.findById(idRecipe));
		model.addAttribute("ingredientList", ingredientRepository.findAll());
		return "recipeIngredientForm";
	}
	
	@PostMapping("/{idRecipe}")
	public String registerRecipeIngredient(@PathVariable("idRecipe") Long idRecipe, 
											@RequestParam("ingredient") Long idIngredient,
											@RequestParam("amount") Double amount,
											@RequestParam("amountType") AmountType amountType, 
											Model model) {
		
		Recipe recipe = recipeRepository.findById(idRecipe).get();
		Ingredient ingredient = ingredientRepository.findById(idIngredient).get();
		
		RecipeIngredient recipeIngredient = new RecipeIngredient(recipe, ingredient, amount, amountType);
		recipeIngredientRepository.save(recipeIngredient);
		
		model.addAttribute("recipeIngredientList", recipeIngredientRepository.findByRecipeId(idRecipe));
		model.addAttribute("recipe", recipe);
		model.addAttribute("ingredientList", ingredientRepository.findAll());
		return "redirect:/recipes/" + Long.toString(idRecipe);
	}
	
	@GetMapping("/edit/{idRecipe}")
	public String showRecipeUpdateForm(@PathVariable("idRecipe") Long idRecipe, Model model) {
		model.addAttribute("recipe", recipeRepository.findById(idRecipe));
		return "recipeUpdateForm";
	}
	
	@PostMapping("/edit/{idRecipe}")
	public String updateRecipe(@PathVariable("idRecipe") Long idRecipe, @Valid Recipe recipe) {
		recipeRepository.save(recipe);
		return "redirect:/recipes";
	}
	
	@GetMapping("/delete/{recipeId}")
	public String deleteRecipe(@PathVariable("recipeId") Long recipeId) {
		recipeRepository.deleteById(recipeId);
		return "redirect:/recipes";
	}
	
}
