package dev.leonardovcl.sweetcontrol.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.leonardovcl.sweetcontrol.model.Inventory;

@Service
public class IngredientService {
	
	@Autowired
	InventoryService inventoryService;

	public Double getIngredientTotalAmount(Long ingredientId) {
		
		Double totalAmount = 0.0;
		
		List<Inventory> inventoryList = inventoryService.findByIngredientIdAndActiveTrueSorted(ingredientId);
		
		for(Inventory inventory: inventoryList) {
			totalAmount += inventory.getAmountLeft();
		}
		
		return totalAmount;
	}
	
}
