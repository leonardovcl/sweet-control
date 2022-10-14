package dev.leonardovcl.sweetcontrol.model;

import java.util.Objects;

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
		KG, G, ML, L, UN;
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
		Inventory other = (Inventory) obj;
		return Objects.equals(id, other.id);
	}
	
	@Override
	public String toString() {
		StringBuilder objString = new StringBuilder();
		objString.append("[Inventory ").append("#").append(this.getId()).append("] ");
		objString.append("Ingredient: (#").append(this.getIngredient().getId()).append(") ").append(this.getIngredient().getName()).append(" ");
		objString.append("(").append(this.getAmount()).append(" ");
		
		switch (this.getAmountType()) {
		case KG:
			objString.append("Kg");
			break;
		case G:
			objString.append("g");
			break;
		case L:
			objString.append("L");
			break;
		case ML:
			objString.append("mL");
			break;
		case UN:
			objString.append("Un");
			break;
			
		default:
			break;
		}
		
		objString.append(" | R$ ");
		objString.append(this.getPrice());
		objString.append(")");
		
		objString.append(" [@");
		objString.append(Integer.toHexString(System.identityHashCode(this)));
		objString.append("]");
				
		return objString.toString();
	}

	public Double getPricePerAmount() {
		
		if (amount != 0.0) {
			return price / amount;
		} else {
			return null;
		}
	}
	
}
