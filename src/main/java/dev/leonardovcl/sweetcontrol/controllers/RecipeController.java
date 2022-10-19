package dev.leonardovcl.sweetcontrol.controllers;

import java.util.ArrayList;
import java.util.List;
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
import dev.leonardovcl.sweetcontrol.services.RecipeService;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
	@Autowired
	private RecipeIngredientRepository recipeIngredientRepository;
	
	@Autowired
	private RecipeService recipeService;
	
	@GetMapping
	public String showRecipes(Model model) {
		model.addAttribute("ingredientList", ingredientRepository.findAll());
		model.addAttribute("recipeList", recipeRepository.findAll());
		return "recipes";
	}
	
	@PostMapping
	public String showRecipesWithFilter(@RequestParam(value = "nameLike", required = false) String nameLike,
										@RequestParam(value = "idFilter", required = false) Long idFilter,
										@RequestParam(value = "idIngredientFilter", required = false) Long idIngredientFilter,
										Model model) {
		
		List<Recipe> recipeList = new ArrayList<>();
		
		if(idFilter != null) {
			Recipe recipeById = recipeRepository.findById(idFilter).isPresent() ? recipeRepository.findById(idFilter).get() : null;
			
			if(recipeById != null) {
				recipeList.add(recipeById);
			}
		} else if (!nameLike.isBlank() && idIngredientFilter == null) {
			recipeList = recipeRepository.findByNameContainingIgnoreCase(nameLike);
		} else if (nameLike.isBlank() && idIngredientFilter != null) {
			recipeList = recipeService.findRecipeByIngredient(idIngredientFilter);
		} else if (!nameLike.isBlank() && idIngredientFilter != null) {
			recipeList = recipeService.findRecipeByIngredientAndNameContaining(idIngredientFilter, nameLike);
		} else {
			recipeList = (List<Recipe>) recipeRepository.findAll();
		}
		
		model.addAttribute("idIngredientFilter", idIngredientFilter);
		model.addAttribute("recipeList", recipeList);
		model.addAttribute("ingredientList", ingredientRepository.findAll());
		
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
