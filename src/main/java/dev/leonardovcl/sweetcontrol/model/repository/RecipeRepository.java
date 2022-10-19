package dev.leonardovcl.sweetcontrol.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import dev.leonardovcl.sweetcontrol.model.Recipe;

public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long> {

	public List<Recipe> findByNameContainingIgnoreCase(String likePattern);
	
	public Optional<Recipe> findById(Long id);
	
}
