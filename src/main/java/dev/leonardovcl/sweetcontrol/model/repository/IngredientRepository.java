package dev.leonardovcl.sweetcontrol.model.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import dev.leonardovcl.sweetcontrol.model.Ingredient;

public interface IngredientRepository extends PagingAndSortingRepository<Ingredient, Long> {

}
