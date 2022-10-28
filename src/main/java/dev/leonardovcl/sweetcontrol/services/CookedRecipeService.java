package dev.leonardovcl.sweetcontrol.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.leonardovcl.sweetcontrol.model.CookedRecipe;
import dev.leonardovcl.sweetcontrol.model.Inventory;
import dev.leonardovcl.sweetcontrol.model.Recipe;
import dev.leonardovcl.sweetcontrol.model.RecipeIngredient;
import dev.leonardovcl.sweetcontrol.model.UsedInventory;
import dev.leonardovcl.sweetcontrol.model.repository.InventoryRepository;
import dev.leonardovcl.sweetcontrol.model.repository.RecipeIngredientRepository;
import dev.leonardovcl.sweetcontrol.model.repository.CookedRecipeRepository;
import dev.leonardovcl.sweetcontrol.model.repository.RecipeRepository;
import dev.leonardovcl.sweetcontrol.model.repository.UsedInventoryRepository;

@Service
public class CookedRecipeService {
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	RecipeIngredientRepository recipeIngredientRepository;
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	@Autowired
	IngredientService ingredientService;
	
	@Autowired
	RecipeService recipeService;
	
	@Autowired
	CookedRecipeRepository cookedRecipeRepository;
	
	@Autowired
	UsedInventoryRepository usedInventoryRepository;
	
	public List<CookedRecipe> recipeListToCookedRecipeList(List<Recipe> recipeList) {
		
		List<List<CookedRecipe>> cookedRecipeLists = recipeList.stream()
				.map(recipe -> cookedRecipeRepository.findByRecipeEntryIdOrderByIdDesc(recipe.getId()))
				.toList();
		
		
		List<CookedRecipe> cookedRecipeList = new ArrayList<>();
		
		cookedRecipeLists.stream()
				.forEach(
						cRecipeList -> {
											cRecipeList.stream()
												.forEach(cookedRecipe -> cookedRecipeList.add(cookedRecipe));
										}
				);
		
		return cookedRecipeList;
	}
	
	public List<CookedRecipe> findCookedRecipeByIngredient(Long idIngredientFilter) {
		
		List<RecipeIngredient> recipeIngredientList = recipeIngredientRepository.findByIngredientEntryId(idIngredientFilter);
		
		List<Recipe> recipeByIngredientList = recipeIngredientList.stream()
				.map(recipeIngredient -> recipeIngredient.getRecipe().getId())
				.map(recipeId -> recipeRepository.findById(recipeId).get()).toList();
		
		Set<Recipe> recipeByIngredientUniqueList = new HashSet<>(recipeByIngredientList);
		
		List<Recipe> recipeList = new ArrayList<>(recipeByIngredientUniqueList);
		
		List<CookedRecipe> cookedRecipeList = recipeListToCookedRecipeList(recipeList);
		
		return cookedRecipeList;
	}
	
	public List<CookedRecipe> findCookedRecipeByIngredientAndNameContaining(Long idIngredientFilter, String likePattern) {
		
		List<Recipe> recipeByIngredientList = recipeService.findRecipeByIngredient(idIngredientFilter);
		
		List<Recipe> recipeList = recipeByIngredientList.stream()
				.filter(recipe -> recipe.getName().toLowerCase().contains(likePattern.toLowerCase()))
				.toList();
		
		List<CookedRecipe> cookedRecipeList = recipeListToCookedRecipeList(recipeList);
		
		return cookedRecipeList;
	}
	
	public void revertCookedRecipe(Long cookedRecipeId) {

		List<UsedInventory> usedInventoryList = usedInventoryRepository.findByCookedRecipeEntryId(cookedRecipeId);
		
		for(UsedInventory usedInventory: usedInventoryList) {

			Inventory inventory = usedInventory.getInventoryEntry();
			Double amountUsed = usedInventory.getInventoryEntryAmount();

			Double amountLeft = inventory.getAmountLeft();
			
			inventory.setAmountLeft(amountLeft + amountUsed);
			inventory.setActive(true);
			
			inventoryRepository.save(inventory);
			
			usedInventoryRepository.delete(usedInventory);
		}
		
		cookedRecipeRepository.deleteById(cookedRecipeId);
	}
	
	public void deleteCookedRecipe(Long cookedRecipeId) {
		
		List<UsedInventory> usedInventories = usedInventoryRepository.findByCookedRecipeEntryId(cookedRecipeId);
		
		for(UsedInventory usedInventory: usedInventories) {
			
			Inventory inventory = usedInventory.getInventoryEntry();
			List<UsedInventory> usedInventoriesList = usedInventoryRepository.findByInventoryEntryId(inventory.getId());
			
			if(inventory.isActive() == false && usedInventoriesList.size() == 1) {
				
				inventoryRepository.delete(inventory);
			}
			
			usedInventoryRepository.delete(usedInventory);
		}
		
		cookedRecipeRepository.deleteById(cookedRecipeId);
	}
}
