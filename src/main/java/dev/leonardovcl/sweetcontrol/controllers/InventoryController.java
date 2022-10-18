package dev.leonardovcl.sweetcontrol.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.leonardovcl.sweetcontrol.model.Inventory;
import dev.leonardovcl.sweetcontrol.model.repository.IngredientRepository;
import dev.leonardovcl.sweetcontrol.model.repository.InventoryRepository;

@Controller
@RequestMapping("/inventories")
public class InventoryController {

	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
	@GetMapping("/{idIngredient}")
	public String showInventories(@PathVariable("idIngredient") Long idIngredient, Model model) {
		model.addAttribute("idIngredient", idIngredient);
		model.addAttribute("inventoryList", inventoryRepository.findByIngredientId(idIngredient));
		return "inventories";
	}
	
	@GetMapping("/register")
	public String showInventoryGenericRegisterForm(Model model) {
		model.addAttribute("inventory", new Inventory());
		model.addAttribute("ingredientList", ingredientRepository.findAll());
		return "inventoryGenericForm";
	}
	
	@PostMapping("/register")
	public String registerGenericInventory(@Valid Inventory inventory) {
		String idIngredient = Long.toString(inventory.getIngredient().getId());
		inventoryRepository.save(inventory);
		return "redirect:/inventories/" + idIngredient;
	}
	
	@GetMapping("/register/{idInventory}")
	public String showInventoryRegisterForm(@PathVariable("idInventory") Long idInventory, Model model) {
		Inventory inventoryObj = new Inventory();
		inventoryObj.setIngredient(ingredientRepository.findById(idInventory).get());
		model.addAttribute("inventory", inventoryObj);
		return "inventoryForm";
	}
	
	@PostMapping("/register/{idInventory}")
	public String registerInventory(@PathVariable("idInventory") Long idInventory, @Valid Inventory inventory) {
		String idIngredient = Long.toString(inventory.getIngredient().getId());
		inventoryRepository.save(inventory);
		return "redirect:/inventories/" + idIngredient;
	}
	
	@GetMapping("/edit/{idInventory}")
	public String showInventoryUpdateForm(@PathVariable("idInventory") Long idInventory, Model model) {
		Optional<Inventory> inventory = inventoryRepository.findById(idInventory);
		model.addAttribute("inventory", inventory);
		model.addAttribute("ingredientList", ingredientRepository.findAll());
		return "inventoryUpdateForm";
	}
	
	@PostMapping("/edit/{idInventory}")
	public String updateInventory(@PathVariable("idInventory") Long idInventory, @Valid Inventory inventory) {
		String idIngredient = Long.toString(inventory.getIngredient().getId());
		inventoryRepository.save(inventory);
		return "redirect:/inventories/" + idIngredient;
	}
	
	@GetMapping("/delete/{idInventory}")
	public String deleteIngredient(@PathVariable("idInventory") Long idInventory) {
		String idIngredient = Long.toString(inventoryRepository.findById(idInventory).get().getIngredient().getId());
		inventoryRepository.deleteById(idInventory);
		return "redirect:/inventories/" + idIngredient;
	}
	
}
