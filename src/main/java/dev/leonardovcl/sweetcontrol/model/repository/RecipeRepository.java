package dev.leonardovcl.sweetcontrol.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import dev.leonardovcl.sweetcontrol.model.Recipe;

public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long> {

	public Page<Recipe> findByNameContainingIgnoreCase(String likePattern, Pageable pageable);
	
	public List<Recipe> findByNameContainingIgnoreCase(String likePattern);
	
}
