package dev.leonardovcl.sweetcontrol.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.leonardovcl.sweetcontrol.model.Inventory;
import dev.leonardovcl.sweetcontrol.model.Recipe;
import dev.leonardovcl.sweetcontrol.model.RecipeIngredient;
import dev.leonardovcl.sweetcontrol.model.UsedInventory;
import dev.leonardovcl.sweetcontrol.model.CookedRecipe;
import dev.leonardovcl.sweetcontrol.model.repository.InventoryRepository;
import dev.leonardovcl.sweetcontrol.model.repository.RecipeIngredientRepository;
import dev.leonardovcl.sweetcontrol.model.repository.CookedRecipeRepository;
import dev.leonardovcl.sweetcontrol.model.repository.RecipeRepository;
import dev.leonardovcl.sweetcontrol.model.repository.UsedInventoryRepository;

@Service
public class RecipeService {
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	RecipeIngredientRepository recipeIngredientRepository;
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	@Autowired
	IngredientService ingredientService;
	
	@Autowired
	CookedRecipeRepository cookedRecipeRepository;
	
	@Autowired
	UsedInventoryRepository usedInventoryRepository;
	
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
		
		List<Recipe> recipeList = new ArrayList<>();
		
		recipeByIngredientList.stream()
				.filter(recipe -> recipe.getName().toLowerCase().contains(likePattern.toLowerCase()))
				.forEach(recipe -> recipeList.add(recipe));
		
		return recipeList;
	}
	
	public Integer calculateRecipesLeft(Long recipeId) {
		
		Integer recipesLeft = 100000;
		
		List<RecipeIngredient> recipeIngredientList = recipeIngredientRepository.findByRecipeId(recipeId);
		
		if(recipeIngredientList.size() == 0) {
			recipesLeft = 0;
		}
		
		for(RecipeIngredient recipeIngredient: recipeIngredientList) {
			
			Long ingredientId = recipeIngredient.getIngredientEntry().getId();
			Double ingredientAmountPerRecipe = recipeIngredient.getRecipeIngredientAmount();
			
			Double ingredientAmountLeft = ingredientService.getIngredientTotalAmount(ingredientId);
			
			Integer recipesLeftPerIngredient = (int) (ingredientAmountLeft / ingredientAmountPerRecipe);
			
			recipesLeft = Integer.min(recipesLeft, recipesLeftPerIngredient);
		}
		
		return recipesLeft;
	}
	
	private class RecipeTotalCostReturn {
		
		private Double recipeTotalCost;
		
		private Map<Inventory, Double> usedInventoryHash = new HashMap<>();

		public RecipeTotalCostReturn() {
			
		}

		public Double getRecipeTotalCost() {
			return recipeTotalCost;
		}

		public void setRecipeTotalCost(Double recipeTotalCost) {
			this.recipeTotalCost = recipeTotalCost;
		}

		public Map<Inventory, Double> getUsedInventoryHash() {
			return usedInventoryHash;
		}
	}
	
	public RecipeTotalCostReturn calculateRecipeTotalCost(Long recipeId) {

		RecipeTotalCostReturn recipeTotalCostReturn = new RecipeTotalCostReturn();
		
		Double totalCost = 0.00;

		List<RecipeIngredient> recipeIngredintList = recipeIngredientRepository.findByRecipeId(recipeId);

		for(RecipeIngredient recipeIngredient: recipeIngredintList) {

			Long ingredientId = recipeIngredient.getIngredientEntry().getId();
			Double ingredientAmount = recipeIngredient.getRecipeIngredientAmount();

			List<Inventory> ingredientInventoryList = inventoryRepository.findByIngredientIdAndActiveTrueOrderByExpirationDateAsc(ingredientId);

			for(Inventory ingredientInventory: ingredientInventoryList) {

				Double amountLeft = ingredientInventory.getAmountLeft();

				if(amountLeft <= ingredientAmount) {
					
					recipeTotalCostReturn.usedInventoryHash.put(ingredientInventory, amountLeft);
					
					ingredientAmount -= amountLeft;
					totalCost += ingredientInventory.getPricePerAmount() * amountLeft;
				} else {
					
					recipeTotalCostReturn.usedInventoryHash.put(ingredientInventory, ingredientAmount);
					
					totalCost += ingredientInventory.getPricePerAmount() * ingredientAmount;
					ingredientAmount = 0.0;
				}

				if(ingredientAmount == 0.0) {
					break;
				}
			}
			
			if(ingredientAmount != 0.0) {
				totalCost = 0.0;
				break;
			}
		}

		recipeTotalCostReturn.setRecipeTotalCost(totalCost);
		
		return recipeTotalCostReturn;
	}
	
	public void cookRecipe(Long recipeId) {
		
		Recipe recipeEntry = recipeRepository.findById(recipeId).isPresent() ? recipeRepository.findById(recipeId).get() : null;
		RecipeTotalCostReturn recipeEntryTotalCostReturn = calculateRecipeTotalCost(recipeId);
		Date makingDate = new Date();
		
		if(recipeEntryTotalCostReturn.getRecipeTotalCost() == 0.0) {
			return;
		}
		
		CookedRecipe cookedRecipe = new CookedRecipe(
				recipeEntry,
				recipeEntryTotalCostReturn.getRecipeTotalCost(),
				makingDate
		);
		
		cookedRecipeRepository.save(cookedRecipe);
		
		for(Inventory recipeInventory: recipeEntryTotalCostReturn.getUsedInventoryHash().keySet()) {
			
			Double amountUsed = recipeEntryTotalCostReturn.getUsedInventoryHash().get(recipeInventory);
			
			UsedInventory usedInventory = new UsedInventory(cookedRecipe, recipeInventory, amountUsed);
			usedInventoryRepository.save(usedInventory);
			
			Double amountLeft = recipeInventory.getAmountLeft();
			recipeInventory.setAmountLeft(amountLeft - amountUsed);
			
			if(recipeInventory.getAmountLeft() == 0.0) {
				recipeInventory.setActive(false);
			}

			inventoryRepository.save(recipeInventory);
		}
	}
}
