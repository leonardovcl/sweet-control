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

@Entity
public class RecipeInventory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "inventory_id")
	private Inventory inventoryEntry;
	
	@NotBlank
	@Column(name = "recipe_amount")
	private Double recipeAmount;
	
	public RecipeInventory() {
		
	}
	
	public RecipeInventory(Recipe recipe, Inventory inventoryEntry, Double recipeAmount) {
		this.setRecipe(recipe);
		this.setInventoryEntry(inventoryEntry);
		this.setRecipeAmount(recipeAmount);
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
	
	public Inventory getInventoryEntry() {
		return inventoryEntry;
	}
	
	public void setInventoryEntry(Inventory inventoryEntry) {
		this.inventoryEntry = inventoryEntry;
	}
	
	public Double getRecipeAmount() {
		return recipeAmount;
	}
	
	public void setRecipeAmount(Double recipeAmount) {
		this.recipeAmount =  recipeAmount;
	}
	
}
