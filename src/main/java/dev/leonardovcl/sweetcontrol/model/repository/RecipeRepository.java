package dev.leonardovcl.sweetcontrol.model.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import dev.leonardovcl.sweetcontrol.model.Recipe;

public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long> {

}
