package dev.leonardovcl.sweetcontrol.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import dev.leonardovcl.sweetcontrol.model.Ingredient;

public interface IngredientRepository extends PagingAndSortingRepository<Ingredient, Long> {

	public List<Ingredient> findByIngredientOwnerId(Long ingredientOwnerId);
	
	public Page<Ingredient> findByIngredientOwnerId(Long ingredientOwnerId, Pageable pageable);
	
	public Page<Ingredient> findByNameContainingIgnoreCaseAndIngredientOwnerId(String likePattern, Long ingredientOwnerId, Pageable pageable);
	
}
