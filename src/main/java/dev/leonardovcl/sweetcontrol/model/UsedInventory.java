package dev.leonardovcl.sweetcontrol.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UsedInventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "cooked_recipe_id")
	private CookedRecipe cookedRecipeEntry;
	
	@ManyToOne
	@JoinColumn(name = "inventory_entry")
	private Inventory inventoryEntry;
	
	@Column(name = "inventory_entry_amount")
	private Double inventoryEntryAmount;

	public UsedInventory() {
		
	}
	
	public UsedInventory(CookedRecipe cookedRecipeEntry, Inventory inventoryEntry, Double inventoryEntryAmount) {
		this.setCookedRecipeEntry(cookedRecipeEntry);
		this.setInventoryEntry(inventoryEntry);
		this.setInventoryEntryAmount(inventoryEntryAmount);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CookedRecipe getCookedRecipeEntry() {
		return cookedRecipeEntry;
	}

	public void setCookedRecipeEntry(CookedRecipe cookedRecipe) {
		this.cookedRecipeEntry = cookedRecipe;
	}

	public Inventory getInventoryEntry() {
		return inventoryEntry;
	}

	public void setInventoryEntry(Inventory inventoryEntry) {
		this.inventoryEntry = inventoryEntry;
	}

	public Double getInventoryEntryAmount() {
		return inventoryEntryAmount;
	}

	public void setInventoryEntryAmount(Double inventoryEntryAmount) {
		this.inventoryEntryAmount = inventoryEntryAmount;
	}

}
