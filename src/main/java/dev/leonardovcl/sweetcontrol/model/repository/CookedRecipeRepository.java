package dev.leonardovcl.sweetcontrol.model.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import dev.leonardovcl.sweetcontrol.model.CookedRecipe;

public interface CookedRecipeRepository extends PagingAndSortingRepository<CookedRecipe, Long> {
	
	public List<CookedRecipe> findByRecipeEntryIdOrderByIdDesc(Long recipeEntryId);
	
	public List<CookedRecipe> findByRecipeEntryNameContaining(String likePattern);
	
}
