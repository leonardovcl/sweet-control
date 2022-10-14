package dev.leonardovcl.sweetcontrol.model.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import dev.leonardovcl.sweetcontrol.model.RecipeIngredient;

public interface RecipeIngredientRepository extends PagingAndSortingRepository<RecipeIngredient, Long> {

}
