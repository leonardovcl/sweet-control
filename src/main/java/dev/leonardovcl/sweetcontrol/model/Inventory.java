package dev.leonardovcl.sweetcontrol.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd 'at' HH:mm:ss z")
	@Temporal(TemporalType.DATE)
	private Date inclusionDate;
	
	@ManyToOne
	private Ingredient ingredient;
	
	@Column(name = "ingredient_amount")
	private Double amount;
	
	@Column(name = "ingredient_amount_left")
	private Double amountLeft;
	
	@Column(name = "ingredient_amount_type")
	private AmountType amountType;
	
	public enum AmountType {
		kg, g, mL, L, Un;
	}
	
	@Column(name = "ingredient_price")
	private Double price;
	
	public Inventory() {
		this.inclusionDate = new Date();
	}
	
	public Inventory (Ingredient ingredient, Double amount, Double price) {
		this.inclusionDate = new Date();
		this.setIngredient(ingredient);
		this.setAmount(amount);
		this.setAmountLeft(amount);
		this.setPrice(price);
	}
	
	public Inventory (Ingredient ingredient, Double amount, AmountType amountType, Double price) {
		this.setIngredient(ingredient);
		this.setAmount(amount);
		this.setAmountLeft(amount);
		this.setAmountType(amountType);
		this.setPrice(price);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getInclusionDate() {
		return inclusionDate;
	}
	
	public void setInclusionDate(Date inclusionDate) {
		this.inclusionDate = inclusionDate;
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
	
	public Double getAmountLeft() {
		return amountLeft;
	}
	
	public void setAmountLeft(Double amountLeft) {
		this.amountLeft = amountLeft;
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
		objString.append("(").append(this.getAmount()).append(" ").append(this.getAmountType().name());
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
	
	public String getPricePerAmountAsString() {
		
		price = getPricePerAmount();
		
		if (price != null) {
			String priceString = String.format("%.2f", price);
			return priceString;
		} else {
			return "";
		}
	}
	
}
