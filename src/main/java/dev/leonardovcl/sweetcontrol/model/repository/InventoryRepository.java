package dev.leonardovcl.sweetcontrol.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import dev.leonardovcl.sweetcontrol.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	public List<Inventory> findByIngredientId(Long idIngredient);
	
	public List<Inventory> findByIngredientIdAndActiveTrue(Long idIngredient, Sort groupSort);
	
	public Page<Inventory> findByIngredientIdAndActiveTrue(Long idIngredient, Pageable pageable);
	
}
