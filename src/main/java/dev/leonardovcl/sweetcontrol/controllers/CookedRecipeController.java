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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.leonardovcl.sweetcontrol.model.CookedRecipe;
import dev.leonardovcl.sweetcontrol.model.Recipe;
import dev.leonardovcl.sweetcontrol.model.SecurityUser;
import dev.leonardovcl.sweetcontrol.model.repository.CookedRecipeRepository;
import dev.leonardovcl.sweetcontrol.model.repository.IngredientRepository;
import dev.leonardovcl.sweetcontrol.model.repository.RecipeRepository;
import dev.leonardovcl.sweetcontrol.model.repository.UsedInventoryRepository;
import dev.leonardovcl.sweetcontrol.model.repository.UserRepository;
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
	private UserRepository userRepository;	
	
	@Autowired
	private CookedRecipeService cookedRecipeService;
	
	@GetMapping
	public String showCookedRecipes(
			@RequestParam(value = "page", required = false,  defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "5") int size,
			@RequestParam(value = "nameLike", required = false, defaultValue = "") String nameLike,
			@RequestParam(value = "idIngredientFilter", required = false) Long idIngredientFilter,
			@AuthenticationPrincipal SecurityUser securityUser,
			Model model) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		
		model.addAttribute("ingredientList", ingredientRepository.findAll());
		
		model.addAttribute("nameLike", nameLike);
		model.addAttribute("idIngredientFilter", idIngredientFilter);
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("recipeEntry.name"));
		Page<CookedRecipe> cookedRecipeList = null;
		
		PagedListHolder<CookedRecipe> cookedRecipeListHolder = new PagedListHolder<>();
		cookedRecipeListHolder.setPageSize(size);
		cookedRecipeListHolder.setSort(new MutableSortDefinition("id", true, true));
		
		List<CookedRecipe> cookedRecipeArrayList = new ArrayList<>();
		
		if (!nameLike.isBlank() && idIngredientFilter == null) {
			
			cookedRecipeArrayList = cookedRecipeService.findCookedRecipeByNameContaining(nameLike, userId);
			
			cookedRecipeListHolder.setSource(cookedRecipeArrayList);
			cookedRecipeListHolder.resort();
			cookedRecipeListHolder.setPage(page);
			
			cookedRecipeList = new PageImpl<>(cookedRecipeListHolder.getPageList(), pageable, cookedRecipeArrayList.size());
			
		} else if (nameLike.isBlank() && idIngredientFilter != null) {
			
			cookedRecipeArrayList = cookedRecipeService.findCookedRecipeByIngredient(idIngredientFilter, userId);
			
			cookedRecipeListHolder.setSource(cookedRecipeArrayList);
			cookedRecipeListHolder.resort();
			cookedRecipeListHolder.setPage(page);
			
			cookedRecipeList = new PageImpl<>(cookedRecipeListHolder.getPageList(), pageable, cookedRecipeArrayList.size());
		
		} else if (!nameLike.isBlank() && idIngredientFilter != null) {
			
			cookedRecipeArrayList = cookedRecipeService.findCookedRecipeByIngredientAndNameContaining(idIngredientFilter, nameLike, userId);
			
			cookedRecipeListHolder.setSource(cookedRecipeArrayList);
			cookedRecipeListHolder.resort();
			cookedRecipeListHolder.setPage(page);
			
			cookedRecipeList = new PageImpl<>(cookedRecipeListHolder.getPageList(), pageable, cookedRecipeArrayList.size());
			
		} else {
			
			cookedRecipeArrayList = cookedRecipeService.findAll(userId);
			
			cookedRecipeListHolder.setSource(cookedRecipeArrayList);
			cookedRecipeListHolder.resort();
			cookedRecipeListHolder.setPage(page);
			
			cookedRecipeList = new PageImpl<>(cookedRecipeListHolder.getPageList(), pageable, cookedRecipeArrayList.size());
		}
		
		model.addAttribute("cookedRecipeList", cookedRecipeList.getContent());
		model.addAttribute("hasPrevious", cookedRecipeList.hasPrevious());
		model.addAttribute("hasNext", cookedRecipeList.hasNext());
		model.addAttribute("totalPages", cookedRecipeList.getTotalPages());
		model.addAttribute("pageNumber", cookedRecipeList.getNumber());
		
		return "cookedrecipes/cookedRecipes";
	}
	
	@GetMapping("/detail/{cookedRecipeId}")
	public String detailCookedRecipe(@PathVariable("cookedRecipeId") Long cookedRecipeId,
									@AuthenticationPrincipal SecurityUser securityUser,
									Model model) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		
		CookedRecipe cookedRecipe = cookedRecipeRepository.findById(cookedRecipeId).get();
		Recipe recipe = recipeRepository.findById(cookedRecipe.getRecipeEntry().getId()).get();
		
		if(recipe.getRecipeOwner().getId() != userId) {
			return "error/accessDenied";
		}
		
		model.addAttribute("cookedRecipe", cookedRecipe);
		model.addAttribute("recipe", recipe);
		model.addAttribute("usedInventoriesList", usedInventoryRepository.findByCookedRecipeEntryId(cookedRecipeId));
		
		return "cookedrecipes/cookedRecipesDetail";
	}
	
	@GetMapping("/revert/{cookedRecipeId}")
	public String revertCookedRecipe(@PathVariable("cookedRecipeId") Long cookedRecipeId,
									@AuthenticationPrincipal SecurityUser securityUser) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		
		CookedRecipe cookedRecipe = cookedRecipeRepository.findById(cookedRecipeId).get();
		Recipe recipe = recipeRepository.findById(cookedRecipe.getRecipeEntry().getId()).get();
		
		if(recipe.getRecipeOwner().getId() != userId) {
			return "error/accessDenied";
		}
		
		cookedRecipeService.revertCookedRecipe(cookedRecipeId);
		
		return "redirect:/cookedrecipes";
	}
	
	@GetMapping("/delete/{cookedRecipeId}")
	public String deleteCookedRecipe(@PathVariable("cookedRecipeId") Long cookedRecipeId,
									@AuthenticationPrincipal SecurityUser securityUser) {
	
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		
		CookedRecipe cookedRecipe = cookedRecipeRepository.findById(cookedRecipeId).get();
		Recipe recipe = recipeRepository.findById(cookedRecipe.getRecipeEntry().getId()).get();
		
		if(recipe.getRecipeOwner().getId() != userId) {
			return "error/accessDenied";
		}
		
		cookedRecipeService.deleteCookedRecipe(cookedRecipeId);
		
		return "redirect:/cookedrecipes";
	}
}
