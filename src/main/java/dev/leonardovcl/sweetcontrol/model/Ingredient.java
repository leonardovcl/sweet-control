package dev.leonardovcl.sweetcontrol.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

@Entity
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(name = "inventory_entry")
	@ManyToOne
	private Inventory inventoryEntry;
	
	@NotBlank
	@Column(name = "ingredient_amount")
	private Double amount;
	
	@NotBlank
	@Column(name = "ingredient_price")
	private Double price;
	
	@Transient
	private Double pricePerAmount;
	
	public Ingredient() {
		
	}
	
	public Ingredient (Inventory inventoryEntry, Double amount, Double price) {
		this.setInvententoryEntry(inventoryEntry);
		this.setAmount(amount);
		this.setPrice(price);
		this.setPricePerAmount();
	}
	
	public Long getId() {
		return id;
	}
	
	public Inventory getInventoryEntry() {
		return inventoryEntry;
	}
	
	public void setInvententoryEntry(Inventory inventoryEntry) {
		this.inventoryEntry = inventoryEntry;
	}
	
	public Double getAmount() {
		return amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
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
		if (amount != 0) {
			pricePerAmount = price / amount; 
		}
	}
	
}
