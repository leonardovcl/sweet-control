package dev.leonardovcl.sweetcontrol.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Must not be blank!")
	@Column(name = "recipe_name", length = 200)
	private String name;
	
	@Column(name = "recipet_description", length = 200)
	private String description;
	
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
	private List<RecipeIngredient> recipeIngredients;
	
	@OneToMany(mappedBy = "recipeEntry", cascade = CascadeType.ALL)
	private List<CookedRecipe> cookedRecipeList;
	
	@ManyToOne
	@JoinColumn(name = "owner")
	private User recipeOwner;
	
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
	
	public List<RecipeIngredient> getRecipeIngredients() {
		return recipeIngredients;
	}
	
	public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}
	
	public List<CookedRecipe> getRecipeMadeList() {
		return cookedRecipeList;
	}

	public void setRecipeMadeList(List<CookedRecipe> cookedRecipeList) {
		this.cookedRecipeList = cookedRecipeList;
	}

	public User getRecipeOwner() {
		return recipeOwner;
	}

	public void setRecipeOwner(User recipeOwner) {
		this.recipeOwner = recipeOwner;
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
	
}
