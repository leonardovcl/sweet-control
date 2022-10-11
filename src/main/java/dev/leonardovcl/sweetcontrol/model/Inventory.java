package dev.leonardovcl.sweetcontrol.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(name = "ingredient_name", length = 200)
	private String name;
	
	@Column(name = "ingredient_description", length = 200)
	private String description;
	
	@OneToMany(mappedBy = "inventoryEntry")
	private List<Ingredient> ingredients;
	
	@OneToMany(mappedBy = "inventoryEntry")
	private List<RecipeInventory> inventoryRecipes;
	
	public Inventory() {
		
	}
	
	public Inventory (String name) {
		this.name = name;
	}
	
	public Inventory (String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	public List<RecipeInventory> getInventoryRecipes() {
		return inventoryRecipes;
	}
	
	public void setInventoryRecipes(List<RecipeInventory> inventoryRecipes) {
		this.inventoryRecipes = inventoryRecipes;
	}
	
}
