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
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(name = "ingredient_name", length = 200)
	private String name;
	
	@Column(name = "ingredient_description", length = 200)
	private String description;
	
	@OneToMany(mappedBy = "ingredient")
	private List<Inventory> inventoryEntries;
	
	@OneToMany(mappedBy = "ingredientEntry")
	private List<RecipeIngredient> ingredientRecipes;
	
	public Ingredient() {
		
	}
	
	public Ingredient (String name) {
		this.setName(name);
	}
	
	public Ingredient (String name, String description) {
		this.setName(name);
		this.setDescription(description);
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
	
	public List<Inventory> getInventoryEntries() {
		return inventoryEntries;
	}
	
	public void setInventoryEntries(List<Inventory> inventoryEntries) {
		this.inventoryEntries = inventoryEntries;
	}
	
	public List<RecipeIngredient> getIngredientRecipes() {
		return ingredientRecipes;
	}
	
	public void setIngredientRecipes(List<RecipeIngredient> ingredientRecipes) {
		this.ingredientRecipes = ingredientRecipes;
	}
	
	@Override
	public String toString() {
		StringBuilder objString = new StringBuilder();
		objString.append("[Ingredient ").append("#").append(this.getId()).append("] ");
		objString.append(this.getName()).append(" ");
		objString.append("(").append(this.getDescription()).append(")");
		
		objString.append(" [@");
		objString.append(Integer.toHexString(System.identityHashCode(this)));
		objString.append("]");
				
		return objString.toString();
	}
	
}
