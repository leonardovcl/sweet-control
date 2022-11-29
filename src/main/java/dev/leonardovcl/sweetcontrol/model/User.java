package dev.leonardovcl.sweetcontrol.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Must not be empty!")
	@Column(name = "user_name", unique=true)
	private String username;
	
	@NotEmpty(message = "Must not be empty!")
	@Column(name = "password")
	private String password;
	
	@OneToMany(mappedBy = "recipeOwner", cascade = CascadeType.ALL)
	private List<Recipe> recipes;
	
	@OneToMany(mappedBy = "ingredientOwner", cascade = CascadeType.ALL)
	private List<Ingredient> ingredients;
	
	public User() {
		
	}

	public User(String username, String password) {
		setUsername(username);
		setPassword(password);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public String toString() {
		StringBuilder objString = new StringBuilder();
		objString.append("[User ").append("#").append(this.getId()).append("] ");
		objString.append(this.getUsername());
		
		objString.append(" [@");
		objString.append(Integer.toHexString(System.identityHashCode(this)));
		objString.append("]");
				
		return objString.toString();
	}

}
