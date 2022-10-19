package dev.leonardovcl.sweetcontrol.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.leonardovcl.sweetcontrol.model.Recipe;
import dev.leonardovcl.sweetcontrol.model.RecipeIngredient;
import dev.leonardovcl.sweetcontrol.model.repository.RecipeIngredientRepository;
import dev.leonardovcl.sweetcontrol.model.repository.RecipeRepository;

@Service
public class RecipeService {
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	RecipeIngredientRepository recipeIngredientRepository;
	
	public List<Recipe> findRecipeByIngredient(Long idIngredientFilter) {
		
		List<RecipeIngredient> recipeIngredientList = recipeIngredientRepository.findByIngredientEntryId(idIngredientFilter);
		
		List<Recipe> recipeByIngredientList = recipeIngredientList.stream()
				.map(recipeIngredient -> recipeIngredient.getRecipe().getId())
				.map(recipeId -> recipeRepository.findById(recipeId).get()).toList();
		
		Set<Recipe> recipeByIngredientUniqueList = new HashSet<>(recipeByIngredientList);
		
		List<Recipe> recipeList = new ArrayList<>(recipeByIngredientUniqueList);
		
		return recipeList;
	}
	
	public List<Recipe> findRecipeByIngredientAndNameContaining(Long idIngredientFilter, String likePattern) {
		
		List<Recipe> recipeByIngredientList = findRecipeByIngredient(idIngredientFilter);
		
		List<Recipe> recipeList = recipeByIngredientList.stream()
				.filter(recipe -> recipe.getName().toLowerCase().contains(likePattern.toLowerCase()))
				.toList();
		
		return recipeList;
	}
	
}
