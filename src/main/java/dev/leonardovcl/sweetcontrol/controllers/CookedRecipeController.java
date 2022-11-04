package dev.leonardovcl.sweetcontrol.controllers;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.leonardovcl.sweetcontrol.model.CookedRecipe;
import dev.leonardovcl.sweetcontrol.model.repository.CookedRecipeRepository;
import dev.leonardovcl.sweetcontrol.model.repository.IngredientRepository;
import dev.leonardovcl.sweetcontrol.model.repository.RecipeRepository;
import dev.leonardovcl.sweetcontrol.model.repository.UsedInventoryRepository;
import dev.leonardovcl.sweetcontrol.services.CookedRecipeService;

@Controller
@RequestMapping("/cookedrecipes")
public class CookedRecipeController {

	@Autowired
	private CookedRecipeRepository cookedRecipeRepository;
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
	@Autowired
	private UsedInventoryRepository usedInventoryRepository;
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private CookedRecipeService cookedRecipeService;
	
	@GetMapping
	public String showCookedRecipes(
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
		Page<CookedRecipe> cookedRecipeList = null;
		
		PagedListHolder<CookedRecipe> cookedRecipeListHolder = new PagedListHolder<>();
		cookedRecipeListHolder.setPageSize(size);
		cookedRecipeListHolder.setSort(new MutableSortDefinition("id", true, true));
		
		List<CookedRecipe> cookedRecipeArrayList = new ArrayList<>();
		
		if(idFilter != null) {
			
			cookedRecipeList = cookedRecipeRepository.findByRecipeEntryIdOrderByIdDesc(idFilter, pageable);
			
		} else if (!nameLike.isBlank() && idIngredientFilter == null) {
			
			cookedRecipeList = cookedRecipeRepository.findByRecipeEntryNameContainingOrderByIdDesc(nameLike, pageable);
			
		} else if (nameLike.isBlank() && idIngredientFilter != null) {
			
			cookedRecipeArrayList = cookedRecipeService.findCookedRecipeByIngredient(idIngredientFilter);
			
			cookedRecipeListHolder.setSource(cookedRecipeArrayList);
			cookedRecipeListHolder.resort();
			cookedRecipeListHolder.setPage(page);
			
			cookedRecipeList = new PageImpl<>(cookedRecipeListHolder.getPageList(), pageable, cookedRecipeArrayList.size());
		
		} else if (!nameLike.isBlank() && idIngredientFilter != null) {
			
			cookedRecipeArrayList = cookedRecipeService.findCookedRecipeByIngredientAndNameContaining(idIngredientFilter, nameLike);
			
			cookedRecipeListHolder.setSource(cookedRecipeArrayList);
			cookedRecipeListHolder.resort();
			cookedRecipeListHolder.setPage(page);
			
			cookedRecipeList = new PageImpl<>(cookedRecipeListHolder.getPageList(), pageable, cookedRecipeArrayList.size());
			
		} else {
			
			cookedRecipeList = cookedRecipeRepository.findAllByOrderByIdDesc(pageable);
		}
		
		model.addAttribute("cookedRecipeList", cookedRecipeList);
		
		return "cookedrecipes/cookedRecipes";
	}
	
	@GetMapping("/detail/{cookedRecipeId}")
	public String detailCookedRecipe(@PathVariable("cookedRecipeId") Long cookedRecipeId, Model model) {
		
		CookedRecipe cookedRecipe = cookedRecipeRepository.findById(cookedRecipeId).get();
		Long recipeId = cookedRecipe.getRecipeEntry().getId();
		
		model.addAttribute("cookedRecipe", cookedRecipe);
		model.addAttribute("recipe", recipeRepository.findById(recipeId).get());
		model.addAttribute("usedInventoriesList", usedInventoryRepository.findByCookedRecipeEntryId(cookedRecipeId));
		
		return "cookedrecipes/cookedRecipesDetail";
	}
	
	@GetMapping("/revert/{cookedRecipeId}")
	public String revertCookedRecipe(@PathVariable("cookedRecipeId") Long cookedRecipeId) {
		
		cookedRecipeService.revertCookedRecipe(cookedRecipeId);
		
		return "redirect:/cookedrecipes";
	}
	
	@GetMapping("/delete/{cookedRecipeId}")
	public String deleteCookedRecipe(@PathVariable("cookedRecipeId") Long cookedRecipeId) {
	
		cookedRecipeService.deleteCookedRecipe(cookedRecipeId);
		
		return "redirect:/cookedrecipes";
	}
}
