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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.leonardovcl.sweetcontrol.model.Recipe;
import dev.leonardovcl.sweetcontrol.model.RecipeIngredient;
import dev.leonardovcl.sweetcontrol.model.SecurityUser;
import dev.leonardovcl.sweetcontrol.model.User;
import dev.leonardovcl.sweetcontrol.model.repository.IngredientRepository;
import dev.leonardovcl.sweetcontrol.model.repository.RecipeIngredientRepository;
import dev.leonardovcl.sweetcontrol.model.repository.RecipeRepository;
import dev.leonardovcl.sweetcontrol.model.repository.UserRepository;
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
	private UserRepository userRepository;
	
	@Autowired
	private RecipeService recipeService;
	
	@GetMapping
	public String showRecipes(
			@RequestParam(value = "page", required = false,  defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "5") int size,
			@RequestParam(value = "nameLike", required = false, defaultValue = "") String nameLike,
			@RequestParam(value = "idIngredientFilter", required = false) Long idIngredientFilter,
			@AuthenticationPrincipal SecurityUser securityUser,
			Model model) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		
		model.addAttribute("ingredientList", ingredientRepository.findByIngredientOwnerId(userId));
		
		model.addAttribute("nameLike", nameLike);
		model.addAttribute("idIngredientFilter", idIngredientFilter);
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
		Page<Recipe> recipeList = null;
		
		PagedListHolder<Recipe> recipeListHolder = new PagedListHolder<>();
		recipeListHolder.setPageSize(size);
		recipeListHolder.setSort(new MutableSortDefinition("id", true, true));
		
		List<Recipe> recipeArrayList = new ArrayList<>();

		if (!nameLike.isBlank() && idIngredientFilter == null) {
			
			recipeList = recipeRepository.findByNameContainingIgnoreCaseAndRecipeOwnerId(nameLike, userId, pageable);
			
		} else if (nameLike.isBlank() && idIngredientFilter != null) {
			
			recipeArrayList = recipeService.findRecipeByIngredient(idIngredientFilter,userId);
			
			recipeListHolder.setSource(recipeArrayList);
			recipeListHolder.resort();
			recipeListHolder.setPage(page);
			
			recipeList = new PageImpl<>(recipeListHolder.getPageList(), pageable, recipeArrayList.size());
			
		} else if (!nameLike.isBlank() && idIngredientFilter != null) {
			
			recipeArrayList = recipeService.findRecipeByIngredientAndNameContaining(idIngredientFilter, nameLike, userId);
			
			recipeListHolder.setSource(recipeArrayList);
			recipeListHolder.resort();
			recipeListHolder.setPage(page);
			
			recipeList = new PageImpl<>(recipeListHolder.getPageList(), pageable, recipeArrayList.size());
			
		} else {
			
			recipeList = recipeRepository.findByRecipeOwnerId(userId, pageable);
			
		}
		
		List<Recipe> recipeListContent = recipeList.getContent();
											
		recipeListContent.stream()
							.forEach(recipe -> {
								recipe.setRecipeTotalCost(recipeService.calculateRecipeTotalCost(recipe.getId()).getRecipeTotalCost());
								recipe.setRecipesLeft(recipeService.calculateRecipesLeft(recipe.getId()));
							});
		
		model.addAttribute("recipeListContent", recipeListContent);
		
		model.addAttribute("hasPrevious", recipeList.hasPrevious());
		model.addAttribute("hasNext", recipeList.hasNext());
		model.addAttribute("totalPages", recipeList.getTotalPages());
		model.addAttribute("pageNumber", recipeList.getNumber());
		
		return "recipes/recipes";
	}
	
	@GetMapping("/register")
	public String showRecipeRegisterForm(Model model) {
		model.addAttribute("recipe", new Recipe());
		return "recipes/recipeForm";
	}
	
	@PostMapping("/register")
	public String registerRecipe(@Valid Recipe recipe,
								BindingResult bindingResult,
								@AuthenticationPrincipal SecurityUser securityUser,
								Model model) {
		
		if(bindingResult.hasErrors()) {
			return "recipes/recipeForm";
		}
		
		User user = userRepository.findByUsername(securityUser.getUsername()).get();
		recipe.setRecipeOwner(user);

		recipeRepository.save(recipe);
		
		return "redirect:/recipes";
	}
	
	@GetMapping("/{idRecipe}")
	public String showRecipeIngredientForm(@PathVariable("idRecipe") Long idRecipe,
											@AuthenticationPrincipal SecurityUser securityUser,
											Model model) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		Recipe recipe = recipeRepository.findById(idRecipe).get();
		
		if(recipe.getRecipeOwner().getId() != userId) {
			return "error/accessDenied";
		}
		
		RecipeIngredient recipeIngredient = new RecipeIngredient();
		recipeIngredient.setRecipe(recipe);
		
		List<RecipeIngredient> recipeIngredientList = recipeIngredientRepository.findByRecipeId(idRecipe, Sort.by("ingredientEntry.name"));
		
		model.addAttribute("recipeIngredient", recipeIngredient);
		model.addAttribute("recipeIngredientList", recipeIngredientList);
		model.addAttribute("recipeIngredientListSize", recipeIngredientList.size());
		model.addAttribute("recipe", recipe);
		model.addAttribute("ingredientList", ingredientRepository.findByIngredientOwnerId(userId));
		
		return "recipes/recipeingredients/recipeIngredientForm";
	}
	
	@PostMapping("/{idRecipe}")
	public String registerRecipeIngredient(@PathVariable("idRecipe") Long idRecipe, 
											@Valid RecipeIngredient recipeIngredient,
											BindingResult bindingResult,
											@AuthenticationPrincipal SecurityUser securityUser,
											Model model) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		Recipe recipe = recipeRepository.findById(idRecipe).get();
		
		if(recipe.getRecipeOwner().getId() != userId) {
			return "error/accessDenied";
		}
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("recipeIngredient",recipeIngredient);
			model.addAttribute("recipeIngredientList", recipeIngredientRepository.findByRecipeId(idRecipe, Sort.by("ingredientEntry.name")));
			model.addAttribute("recipe", recipe);
			model.addAttribute("ingredientList", ingredientRepository.findByIngredientOwnerId(userId));
			
			return "recipes/recipeingredients/recipeIngredientForm";
		}
		
		recipeIngredientRepository.save(recipeIngredient);
		
		return "redirect:/recipes/" + idRecipe;
	}
	
	@GetMapping("/edit/{idRecipe}")
	public String showRecipeUpdateForm(@PathVariable("idRecipe") Long idRecipe,
										@AuthenticationPrincipal SecurityUser securityUser,
										Model model) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		Recipe recipe = recipeRepository.findById(idRecipe).get();
		
		if(recipe.getRecipeOwner().getId() != userId) {
			return "error/accessDenied";
		}
		
		model.addAttribute("recipe", recipe);
		return "recipes/recipeUpdateForm";
	}
	
	@PostMapping("/edit/{idRecipe}")
	public String updateRecipe(@PathVariable("idRecipe") Long idRecipe,
								@Valid Recipe recipe,
								BindingResult bindingResult,
								@AuthenticationPrincipal SecurityUser securityUser,
								Model model) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		
		if(recipe.getRecipeOwner().getId() != userId) {
			return "error/accessDenied";
		}
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("recipe", recipe);
			return "recipes/recipeUpdateForm";
		}
		
		recipeRepository.save(recipe);
		return "redirect:/recipes";
	}
	
	@GetMapping("/delete/{recipeId}")
	public String deleteRecipe(@PathVariable("recipeId") Long recipeId,
								@AuthenticationPrincipal SecurityUser securityUser) {

		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		Recipe recipe = recipeRepository.findById(recipeId).get();
		
		if(recipe.getRecipeOwner().getId() != userId) {
			return "error/accessDenied";
		}
		
		recipeRepository.deleteById(recipeId);
		return "redirect:/recipes";
	}
	
	@GetMapping("/cook/{recipeId}")
	public String cookRecipe(@PathVariable("recipeId") Long recipeId,
							@AuthenticationPrincipal SecurityUser securityUser) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		Recipe recipe = recipeRepository.findById(recipeId).get();
		
		if(recipe.getRecipeOwner().getId() != userId) {
			return "error/accessDenied";
		}
		
		recipeService.cookRecipe(recipeId);
		return "redirect:/cookedrecipes";
	}
	
}
