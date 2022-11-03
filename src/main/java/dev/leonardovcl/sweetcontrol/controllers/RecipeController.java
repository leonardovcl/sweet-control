package dev.leonardovcl.sweetcontrol.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.leonardovcl.sweetcontrol.model.Ingredient;
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
	public String showRecipes(
			@RequestParam(value = "page", required = false,  defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "5") int size,
			@RequestParam(value = "idFilter", required = false) Long idFilter,
			@RequestParam(value = "nameLike", required = false, defaultValue = "") String nameLike,
			@RequestParam(value = "idIngredientFilter", required = false) Long idIngredientFilter,
			Model model) {
		
		model.addAttribute("ingredientList", ingredientRepository.findAll());
		
		model.addAttribute("idFilter", idFilter);
		model.addAttribute("nameLike", nameLike);
		model.addAttribute("idIngredientFilter", idIngredientFilter);
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
		Page<Recipe> recipeList = null;
		
		PagedListHolder<Recipe> recipeListHolder = new PagedListHolder<>();
		recipeListHolder.setPageSize(size);
		recipeListHolder.setSort(new MutableSortDefinition("id", true, true));
		
		List<Recipe> recipeArrayList = new ArrayList<>();
		
		if(idFilter != null) {
			Recipe recipeById = recipeRepository.findById(idFilter).isPresent() ? recipeRepository.findById(idFilter).get() : null;
			
			if(recipeById != null) {
				recipeArrayList.add(recipeById);
			}
			
			recipeList = new PageImpl<Recipe>(recipeArrayList, pageable, recipeArrayList.size());

			
		} else if (!nameLike.isBlank() && idIngredientFilter == null) {
			
			recipeList = recipeRepository.findByNameContainingIgnoreCase(nameLike, pageable);
			
		} else if (nameLike.isBlank() && idIngredientFilter != null) {
			
			recipeArrayList = recipeService.findRecipeByIngredient(idIngredientFilter);
			
			recipeListHolder.setSource(recipeArrayList);
			recipeListHolder.resort();
			recipeListHolder.setPage(page);
			
			recipeList = new PageImpl<>(recipeListHolder.getPageList(), pageable, recipeArrayList.size());
			
		} else if (!nameLike.isBlank() && idIngredientFilter != null) {
			
			recipeArrayList = recipeService.findRecipeByIngredientAndNameContaining(idIngredientFilter, nameLike);
			
			recipeListHolder.setSource(recipeArrayList);
			recipeListHolder.resort();
			recipeListHolder.setPage(page);
			
			recipeList = new PageImpl<>(recipeListHolder.getPageList(), pageable, recipeArrayList.size());
			
		} else {
			
			recipeList = recipeRepository.findAll(pageable);
			
		}
		
		model.addAttribute("recipeList", recipeList);
		
		return "recipes/recipes";
	}
	
	@GetMapping("/register")
	public String showRecipeRegisterForm(Model model) {
		model.addAttribute("recipe", new Recipe());
		return "recipes/recipeForm";
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
											@RequestParam("amount") Double amount) {
		
		Recipe recipe = recipeRepository.findById(idRecipe).get();
		Ingredient ingredient = ingredientRepository.findById(idIngredient).get();
		
		RecipeIngredient recipeIngredient = new RecipeIngredient(recipe, ingredient, amount);
		recipeIngredientRepository.save(recipeIngredient);
		
		return "redirect:/recipes/" + idRecipe;
	}
	
	@GetMapping("/edit/{idRecipe}")
	public String showRecipeUpdateForm(@PathVariable("idRecipe") Long idRecipe, Model model) {
		model.addAttribute("recipe", recipeRepository.findById(idRecipe));
		return "recipes/recipeUpdateForm";
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
	
	@GetMapping("/cook/{recipeId}")
	public String cookRecipe(@PathVariable("recipeId") Long recipeId) {
		recipeService.cookRecipe(recipeId);
		return "redirect:/cookedrecipes";
	}
	
}
