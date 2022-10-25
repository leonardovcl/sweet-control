package dev.leonardovcl.sweetcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.leonardovcl.sweetcontrol.model.CookedRecipe;
import dev.leonardovcl.sweetcontrol.model.repository.CookedRecipeRepository;
import dev.leonardovcl.sweetcontrol.model.repository.RecipeRepository;
import dev.leonardovcl.sweetcontrol.model.repository.UsedInventoryRepository;
import dev.leonardovcl.sweetcontrol.services.RecipeService;

@Controller
@RequestMapping("/cookedrecipes")
public class CookedRecipeController {

	@Autowired
	private CookedRecipeRepository cookedRecipeRepository;
	
	@Autowired
	UsedInventoryRepository usedInventoryRepository;
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	private RecipeService recipeService;
	
	@GetMapping
	public String showCookedRecipes(Model model) {
		
		model.addAttribute("cookedRecipeList", cookedRecipeRepository.findAllByOrderByIdDesc());
		
		return "cookedRecipes";
	}
	
	@PostMapping
	public String showCookedRecipesWithFilter(@RequestParam(value = "idFilter", required = false) Long idFilter,
											@RequestParam(value = "nameLike", required = false) String nameLike,
											Model model) {
		
		if(idFilter != null) {
			model.addAttribute("cookedRecipeList", cookedRecipeRepository.findByRecipeEntryIdOrderByIdDesc(idFilter));
		} else if (nameLike != null) {
			model.addAttribute("cookedRecipeList", cookedRecipeRepository.findByRecipeEntryNameContainingOrderByIdDesc(nameLike));
		} else {
			model.addAttribute("cookedRecipeList", cookedRecipeRepository.findAllByOrderByIdDesc());
		}
		
		return "cookedRecipes";
	}
	
	@GetMapping("/detail/{cookedRecipeId}")
	public String detailCookedRecipe(@PathVariable("cookedRecipeId") Long cookedRecipeId, Model model) {
		
		CookedRecipe cookedRecipe = cookedRecipeRepository.findById(cookedRecipeId).get();
		Long recipeId = cookedRecipe.getRecipeEntry().getId();
		
		model.addAttribute("cookedRecipe", cookedRecipe);
		model.addAttribute("recipe", recipeRepository.findById(recipeId));
//		model.addAttribute("recipeIngredientList", recipeIngredientRepository.findByRecipeId(recipeId));
		model.addAttribute("usedInventoriesList", usedInventoryRepository.findByCookedRecipeEntryId(cookedRecipeId));
		
		return "cookedRecipesDetail";
	}
	
	@GetMapping("/revert/{cookedRecipeId}")
	public String revertCookedRecipe(@PathVariable("cookedRecipeId") Long cookedRecipeId) {
		
		recipeService.revertCookRecipe(cookedRecipeId);
		
		return "redirect:/cookedrecipes";
	}
	
	@GetMapping("/delete/{cookedRecipeId}")
	public String deleteCookedRecipe(@PathVariable("cookedRecipeId") Long cookedRecipeId) {
	
		recipeService.deleteCookedRecipe(cookedRecipeId);
		
		return "redirect:/cookedrecipes";
	}
}
