package dev.leonardovcl.sweetcontrol.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd 'at' HH:mm:ss z")
	@Temporal(TemporalType.DATE)
	private Date inclusionDate;
	
	@NotNull(message = "Must not be Null!")
	@ManyToOne
	private Ingredient ingredient;
	
	@Column(name = "inventory_active")
	private Boolean active;
	
	@NotNull(message = "Must not be Null!")
	@PositiveOrZero(message = "Must be at least 0!")
	@Column(name = "ingredient_amount")
	private Double amount;
	
	@NotNull(message = "Must not be Null!")
	@PositiveOrZero(message = "Must be at least 0!")
	@Column(name = "ingredient_amount_left")
	private Double amountLeft;
	
	@NotNull(message = "Must not be Null!")
	@PositiveOrZero(message = "Must be at least 0!")
	@Column(name = "ingredient_price")
	private Double price;
	
	@Column(name = "price_per_amount")
	private Double pricePerAmount;
	
	@NotNull(message = "Must not be Null!")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date expirationDate;
	
	@OneToMany(mappedBy = "inventoryEntry", cascade = CascadeType.ALL)
	private List<UsedInventory> usedInventoryList;
	
	public Inventory() {
		this.inclusionDate = new Date();
		this.active = true;
	}
	
	public Inventory (Ingredient ingredient, Double amount, Double price) {
		this.inclusionDate = new Date();
		this.active = true;
		this.setIngredient(ingredient);
		this.setAmount(amount);
		this.setAmountLeft(amount);
		this.setPrice(price);
		this.setPricePerAmount();
	}
	
	public Inventory (Ingredient ingredient, Double amount, Double amountLeft, Double price) {
		this.inclusionDate = new Date();
		this.active = true;
		this.setIngredient(ingredient);
		this.setAmount(amount);
		this.setAmountLeft(amountLeft);
		this.setPrice(price);
		this.setPricePerAmount();
	}
	
	public Inventory (Ingredient ingredient, Double amount, Double amountLeft, Double price, Date expirationDate) {
		this.inclusionDate = new Date();
		this.active = true;
		this.setIngredient(ingredient);
		this.setAmount(amount);
		this.setAmountLeft(amountLeft);
		this.setPrice(price);
		this.setPricePerAmount();
		this.setExpirationDate(expirationDate);
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
	
	public Boolean isActive() {
		return active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
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
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getPricePerAmount() {
		return pricePerAmount;
	}
	
	public void setPricePerAmount() {
		this.pricePerAmount = price / amount;
	}
	
//	public String getPricePerAmountAsString() {
//		
//		price = getPricePerAmount();
//		
//		if (price != null) {
//			String priceString = String.format("%.2f", price);
//			return priceString;
//		} else {
//			return "";
//		}
//	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public List<UsedInventory> getUsedInventoryList() {
		return usedInventoryList;
	}

	public void setUsedInventoryList(List<UsedInventory> usedInventoryList) {
		this.usedInventoryList = usedInventoryList;
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
		objString.append("(").append(this.getAmount()).append(" ").append(this.getIngredient().getAmountType().name());
		objString.append(" | R$ ");
		objString.append(this.getPrice());
		objString.append(")");
		
		objString.append(" [@");
		objString.append(Integer.toHexString(System.identityHashCode(this)));
		objString.append("]");
				
		return objString.toString();
	}
	
}
