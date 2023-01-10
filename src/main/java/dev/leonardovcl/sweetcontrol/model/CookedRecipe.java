package dev.leonardovcl.sweetcontrol.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class CookedRecipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "recipe_id")
	private Recipe recipeEntry;
	
	@Column(name = "recipe_total_cost")
	private Double recipeEntryTotalCost;
	
	@Column(name = "making_date", columnDefinition="TIMESTAMP")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date makingDate;
	
	@OneToMany(mappedBy = "cookedRecipeEntry", cascade = CascadeType.ALL)
	private List<UsedInventory> usedInventoryList;

	public CookedRecipe() {
		this.setMakingDate(new Date());
	}
	
	public CookedRecipe(Recipe recipeEntry, Double recipeEntryTotalCost) {
		this.setRecipeEntry(recipeEntry);
		this.setRecipeEntryTotalCost(recipeEntryTotalCost);
		this.setMakingDate(new Date());
	}
	

	public CookedRecipe(Recipe recipeEntry, Double recipeEntryTotalCost, Date makingDate) {
		this.setRecipeEntry(recipeEntry);
		this.setRecipeEntryTotalCost(recipeEntryTotalCost);
		this.setMakingDate(makingDate);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Recipe getRecipeEntry() {
		return recipeEntry;
	}

	public void setRecipeEntry(Recipe recipeEntry) {
		this.recipeEntry = recipeEntry;
	}
	
	public Double getRecipeEntryTotalCost() {
		return recipeEntryTotalCost;
	}

	public void setRecipeEntryTotalCost(Double recipeEntryTotalCost) {
		this.recipeEntryTotalCost = recipeEntryTotalCost;
	}
	
	public Date getMakingDate() {
		return makingDate;
	}

	public void setMakingDate(Date makingDate) {
		this.makingDate = makingDate;
	}

	public List<UsedInventory> getUsedInventoryList() {
		return usedInventoryList;
	}

	public void setUsedInventoryList(List<UsedInventory> usedInventoryList) {
		this.usedInventoryList = usedInventoryList;
	}

}
