package dev.leonardovcl.sweetcontrol.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.leonardovcl.sweetcontrol.model.Ingredient;
import dev.leonardovcl.sweetcontrol.model.Inventory;
import dev.leonardovcl.sweetcontrol.model.repository.IngredientRepository;
import dev.leonardovcl.sweetcontrol.model.repository.InventoryRepository;
import dev.leonardovcl.sweetcontrol.model.repository.UsedInventoryRepository;
import dev.leonardovcl.sweetcontrol.services.InventoryService;

@Controller
@RequestMapping("/inventories")
public class InventoryController {

	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	InventoryService inventoryService;
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
	@Autowired
	private UsedInventoryRepository usedInventoryRepository;
	
	@GetMapping("/{idIngredient}")
	public String showInventories(
			@PathVariable("idIngredient") Long idIngredient,
			@RequestParam(value = "page", required = false,  defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "5") int size,
			Model model) {
		
		Pageable pageable = inventoryService.pageableSorted(page, size);
		
		model.addAttribute("idIngredient", idIngredient);
		model.addAttribute("ingredient", ingredientRepository.findById(idIngredient).get());

		
		model.addAttribute("inventoryList", inventoryRepository.findByIngredientIdAndActiveTrue(idIngredient, pageable));
		
		return "inventories/inventories";
	}
	
	@GetMapping("/register")
	public String showInventoryGenericRegisterForm(Model model) {
		model.addAttribute("inventory", new Inventory());
		model.addAttribute("ingredientList", ingredientRepository.findAll());
		return "inventories/inventoryGenericForm";
	}
	
	@PostMapping("/register")
	public String registerGenericInventory(@Valid Inventory inventory,
											BindingResult bindingResult,
											Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("inventory", inventory);
			model.addAttribute("ingredientList", ingredientRepository.findAll());
			
			return "inventories/inventoryGenericForm";
		}
		
		String idIngredient = Long.toString(inventory.getIngredient().getId());
		inventoryRepository.save(inventory);
		
		return "redirect:/inventories/" + idIngredient;
	}
	
	@GetMapping("/register/{idIngredient}")
	public String showInventoryRegisterForm(@PathVariable("idIngredient") Long idIngredient, Model model) {
		
		Inventory inventoryObj = new Inventory();
		Ingredient ingredientObj = ingredientRepository.findById(idIngredient).get();
		
		inventoryObj.setIngredient(ingredientObj);
		
		model.addAttribute("inventory", inventoryObj);
		
		return "inventories/inventoryForm";
	}
	
	@PostMapping("/register/{idIngredient}")
	public String registerInventory(@PathVariable("idIngredient") Long idIngredient,
									@Valid Inventory inventory,
									BindingResult bindingResult,
									Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("inventory", inventory);
			return "inventories/inventoryForm";
		}
		
		inventory.setPricePerAmount();
		inventoryRepository.save(inventory);
		
		return "redirect:/inventories/" + idIngredient;
	}
	
	@GetMapping("/edit/{idInventory}")
	public String showInventoryUpdateForm(@PathVariable("idInventory") Long idInventory, Model model) {
		
		model.addAttribute("usedInventoryExists", usedInventoryRepository.existsByInventoryEntryId(idInventory));
		
		Inventory inventory = inventoryRepository.findById(idInventory).get();
		model.addAttribute("inventory", inventory);
		
		return "inventories/inventoryUpdateForm";
	}
	
	@PostMapping("/edit/{idInventory}")
	public String updateInventory(@PathVariable("idInventory") Long idInventory,
									@Valid Inventory inventory,
									BindingResult bindingResult,
									Model model) {
		
		if(bindingResult.hasErrors()) {
			
			model.addAttribute("inventory", inventory);
			
			return "inventories/inventoryUpdateForm";
		}
		
		String idIngredient = Long.toString(inventory.getIngredient().getId());
		
		inventory.setPricePerAmount();
		inventoryRepository.save(inventory);
		
		return "redirect:/inventories/" + idIngredient;
	}
	
	@GetMapping("/delete/{idInventory}")
	public String deleteInventory(@PathVariable("idInventory") Long idInventory) {
		String idIngredient = Long.toString(inventoryRepository.findById(idInventory).get().getIngredient().getId());
		inventoryRepository.deleteById(idInventory);
		return "redirect:/inventories/" + idIngredient;
	}
	
	@GetMapping("/deleteExpired/{ingredientId}")
	public String deleteExpiredInventory(@PathVariable("ingredientId") Long ingredientId) {
		inventoryService.deleteExpiredInventories(ingredientId);
		return "redirect:/inventories/" + ingredientId;
	}
	
}
