package dev.leonardovcl.sweetcontrol.model.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import dev.leonardovcl.sweetcontrol.model.UsedInventory;

public interface UsedInventoryRepository extends PagingAndSortingRepository<UsedInventory, Long> {
	
	public List<UsedInventory> findByCookedRecipeEntryId(Long cookedRecipeEntryId);
	
	public Boolean existsByInventoryEntryId(Long inventoryEntryId);
	
}
