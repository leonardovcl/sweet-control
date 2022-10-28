package dev.leonardovcl.sweetcontrol.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import dev.leonardovcl.sweetcontrol.model.CookedRecipe;

public interface CookedRecipeRepository extends PagingAndSortingRepository<CookedRecipe, Long> {

	public Page<CookedRecipe> findAllByOrderByIdDesc(Pageable pageable);
	
	public List<CookedRecipe> findByRecipeEntryIdOrderByIdDesc(Long recipeEntryId);
	
	public Page<CookedRecipe> findByRecipeEntryIdOrderByIdDesc(Long recipeEntryId, Pageable pageable);
	
	public Page<CookedRecipe> findByRecipeEntryNameContainingOrderByIdDesc(String likePattern, Pageable pageable);
	
}
