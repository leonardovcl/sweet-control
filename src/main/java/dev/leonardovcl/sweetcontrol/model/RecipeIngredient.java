package dev.leonardovcl.sweetcontrol.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import dev.leonardovcl.sweetcontrol.model.Inventory.AmountType;

@Entity
public class RecipeIngredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ingredient_id")
	private Ingredient ingredientEntry;
	
	@NotBlank
	@Column(name = "recipe_ingredient_amount")
	private Double recipeIngredientAmount;
	
	@NotBlank
	@Column(name = "recipe_ingredient_amount_type")
	private AmountType recipeIngredientAmountType;
	
	public RecipeIngredient() {
		
	}
	
	public RecipeIngredient(Recipe recipe, Ingredient ingredientEntry, Double recipeIngredientAmount, AmountType recipeIngredientAmountType) {
		this.setRecipe(recipe);
		this.setIngredientEntry(ingredientEntry);
		this.setRecipeIngredientAmount(recipeIngredientAmount);
		this.setRecipeIngredientAmountType(recipeIngredientAmountType);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Recipe getRecipe() {
		return recipe;
	}
	
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
	public Ingredient getIngredientEntry() {
		return ingredientEntry;
	}
	
	public void setIngredientEntry(Ingredient ingredientEntry) {
		this.ingredientEntry = ingredientEntry;
	}
	
	public Double getRecipeIngredientAmount() {
		return recipeIngredientAmount;
	}
	
	public void setRecipeIngredientAmount(Double recipeIngredientAmount) {
		this.recipeIngredientAmount =  recipeIngredientAmount;
	}
	
	public AmountType getRecipeIngredientAmountType() {
		return recipeIngredientAmountType;
	}
	
	public void setRecipeIngredientAmountType(AmountType recipeIngredientAmountType) {
		this.recipeIngredientAmountType = recipeIngredientAmountType;
	}
	
}
