package dev.leonardovcl.sweetcontrol.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import dev.leonardovcl.sweetcontrol.model.Ingredient;

public interface IngredientRepository extends PagingAndSortingRepository<Ingredient, Long> {

	public Page<Ingredient> findByNameContainingIgnoreCase(String likePattern, Pageable pageable);
	
}
