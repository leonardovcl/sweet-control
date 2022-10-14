package dev.leonardovcl.sweetcontrol.model;

import java.util.List;
import java.util.Objects;

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
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recipe other = (Recipe) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		StringBuilder objString = new StringBuilder();
		objString.append("[Recipe ").append("#").append(this.getId()).append("] ");
		objString.append(this.getName()).append(" ");
		objString.append("(").append(this.getDescription()).append(")");
		
		objString.append(" [@");
		objString.append(Integer.toHexString(System.identityHashCode(this)));
		objString.append("]");
				
		return objString.toString();
	}
	
	public Double getRecipeTotalCost() {
		Double totalCost = 0.00;
		
		for (RecipeIngredient recipeIngredient: recipeIngredients) {
			
			Ingredient ingredient = recipeIngredient.getIngredientEntry();
			Double totalIngredientAmount = 0.00;
			
			List<Inventory> inventoryEntries = ingredient.getInventoryEntries();
			
			for (Inventory inventoryEntry: inventoryEntries) {
				Double amountNeeded = recipeIngredient.getRecipeIngredientAmount() - totalIngredientAmount;
				
				if (inventoryEntry.getAmount() < amountNeeded) {
					totalIngredientAmount += inventoryEntry.getAmount();
					totalCost += inventoryEntry.getAmount() * inventoryEntry.getPricePerAmount();
				} else {
					totalIngredientAmount += amountNeeded;
					totalCost += amountNeeded * inventoryEntry.getPricePerAmount();
				}
				
				if (totalIngredientAmount.doubleValue() == 
					recipeIngredient.getRecipeIngredientAmount().doubleValue()) {
					break;
				}
			}
		}
		
		return totalCost;
	}
	
}
