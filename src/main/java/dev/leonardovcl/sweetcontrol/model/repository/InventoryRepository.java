package dev.leonardovcl.sweetcontrol.model.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import dev.leonardovcl.sweetcontrol.model.Inventory;

public interface InventoryRepository extends PagingAndSortingRepository<Inventory, Long> {

}
