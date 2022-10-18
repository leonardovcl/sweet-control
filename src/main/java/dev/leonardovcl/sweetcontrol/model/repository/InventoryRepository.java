package dev.leonardovcl.sweetcontrol.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.leonardovcl.sweetcontrol.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	public List<Inventory> findByIngredientId(Long idIngredient);
	
}
