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
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(name = "recipe_name", length = 200)
	private String name;
	
	@Column(name = "recipet_description", length = 200)
	private String description;
	
	@OneToMany(mappedBy = "recipe")
	private List<RecipeIngredient> recipeIngredients;
	
	 public Recipe() {
		 
	 }
	 
	 public Recipe(String name) {
		 this.setName(name);
	 }
	 
	 public Recipe(String name, String description) {
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
	
	public List<RecipeIngredient> getRecipeInventories() {
		return recipeIngredients;
	}
	
	public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}
	
	public Double recipeTotalCost() {
		// TODO: Implement Total cost evaluation!
		Double totalCost = 0.00;
		return totalCost;
	}
	
}
