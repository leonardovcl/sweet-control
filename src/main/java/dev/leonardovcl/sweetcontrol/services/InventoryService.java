package dev.leonardovcl.sweetcontrol.services;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import dev.leonardovcl.sweetcontrol.model.Inventory;
import dev.leonardovcl.sweetcontrol.model.repository.InventoryRepository;

@Service
public class InventoryService {
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	private Sort sort = Sort.by(Sort.Direction.ASC, "expirationDate")
					.and(Sort.by(Sort.Direction.ASC, "amountLeft"))
					.and(Sort.by(Sort.Direction.ASC, "pricePerAmount"))
					.and(Sort.by(Sort.Direction.ASC, "inclusionDate"));

	public List<Inventory> findByIngredientIdAndActiveTrueSorted(Long ingredientId) {
		return inventoryRepository.findByIngredientIdAndActiveTrue(ingredientId, sort);
	}
	
	public Pageable pageableSorted(int page, int size) {
		return PageRequest.of(page, size, sort);
	}
	
	public void deleteExpiredInventories(Long ingredientId) {
		
		List<Inventory> inventoryList = inventoryRepository.findByIngredientId(ingredientId);
		
		Predicate<Inventory> isExpired = inventory -> {
			return inventory.getExpirationDate().before(new Date());
		};
		
		inventoryList.stream()
						.filter(isExpired)
							.forEach(
								inventory -> {
									inventoryRepository.delete(inventory);
								}
							);
	}
	
}
