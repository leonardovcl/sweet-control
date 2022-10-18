package dev.leonardovcl.sweetcontrol.model.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import dev.leonardovcl.sweetcontrol.model.Ingredient;

public interface IngredientRepository extends PagingAndSortingRepository<Ingredient, Long> {

	public List<Ingredient> findByNameContainingIgnoreCase(String likePattern);
	
}
