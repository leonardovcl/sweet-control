package dev.leonardovcl.sweetcontrol.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(name = "ingredient_type")
	@ManyToOne
	private Ingredient ingredient;
	
	@NotBlank
	@Column(name = "ingredient_amount")
	private Double amount;
	
	@NotBlank
	@Column(name = "ingredient_amount_type")
	private AmountType amountType;
	
	public enum AmountType {
		KG, G, ML, L;
	}
	
	@NotBlank
	@Column(name = "ingredient_price")
	private Double price;
	
	public Inventory() {
		
	}
	
	public Inventory (Ingredient ingredient, Double amount, Double price) {
		this.setIngredient(ingredient);
		this.setAmount(amount);
		this.setPrice(price);
	}
	
	public Inventory (Ingredient ingredient, Double amount, AmountType amountType, Double price) {
		this.setIngredient(ingredient);
		this.setAmount(amount);
		this.setAmountType(amountType);
		this.setPrice(price);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Ingredient getIngredient() {
		return ingredient;
	}
	
	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}
	
	public Double getAmount() {
		return amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public AmountType getAmountType() {
		return amountType;
	}
	
	public void setAmountType(AmountType amountType) {
		this.amountType = amountType;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getPricePerAmount() {
		
		if (amount != 0.0) {
			return price / amount;
		} else {
			return null;
		}
	}
	
}
