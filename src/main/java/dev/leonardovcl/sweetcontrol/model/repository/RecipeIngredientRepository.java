package dev.leonardovcl.sweetcontrol.model.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import dev.leonardovcl.sweetcontrol.model.RecipeIngredient;

public interface RecipeIngredientRepository extends PagingAndSortingRepository<RecipeIngredient, Long> {
	
	public List<RecipeIngredient> findByRecipeId(Long idRecipe, Sort sort);
	
	public List<RecipeIngredient> findByIngredientEntryId(Long idIngredient);
}
