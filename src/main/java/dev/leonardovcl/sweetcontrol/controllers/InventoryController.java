package dev.leonardovcl.sweetcontrol.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import dev.leonardovcl.sweetcontrol.model.SecurityUser;
import dev.leonardovcl.sweetcontrol.model.repository.IngredientRepository;
import dev.leonardovcl.sweetcontrol.model.repository.InventoryRepository;
import dev.leonardovcl.sweetcontrol.model.repository.UsedInventoryRepository;
import dev.leonardovcl.sweetcontrol.model.repository.UserRepository;
import dev.leonardovcl.sweetcontrol.services.InventoryService;

@Controller
@RequestMapping("/inventories")
public class InventoryController {

	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
	@Autowired
	private UsedInventoryRepository usedInventoryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/{idIngredient}")
	public String showInventories(
			@PathVariable("idIngredient") Long idIngredient,
			@RequestParam(value = "page", required = false,  defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "5") int size,
			@AuthenticationPrincipal SecurityUser securityUser,
			Model model) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		Ingredient ingredient = ingredientRepository.findById(idIngredient).get();
		
		if(ingredient.getIngredientOwner().getId() != userId) {
			return "error/accessDenied";
		}
		
		Pageable pageable = inventoryService.pageableSorted(page, size);
		Page<Inventory> inventoryList = inventoryRepository.findByIngredientIdAndActiveTrue(idIngredient, pageable);
		
		model.addAttribute("ingredient", ingredient);
		model.addAttribute("inventoryList", inventoryList.getContent());
		model.addAttribute("hasPrevious", inventoryList.hasPrevious());
		model.addAttribute("hasNext", inventoryList.hasNext());
		model.addAttribute("totalPages", inventoryList.getTotalPages());
		
		return "inventories/inventories";
	}
	
	@GetMapping("/register")
	public String showInventoryGenericRegisterForm(@AuthenticationPrincipal SecurityUser securityUser,
													Model model) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		
		model.addAttribute("inventory", new Inventory());
		model.addAttribute("ingredientList", ingredientRepository.findByIngredientOwnerId(userId));
		
		return "inventories/inventoryGenericForm";
	}
	
	@PostMapping("/register")
	public String registerGenericInventory(@Valid Inventory inventory,
											BindingResult bindingResult,
											@AuthenticationPrincipal SecurityUser securityUser,
											Model model) {
										
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("inventory", inventory);
			model.addAttribute("ingredientList", ingredientRepository.findByIngredientOwnerId(userId));
			
			return "inventories/inventoryGenericForm";
		}
		
		String idIngredient = Long.toString(inventory.getIngredient().getId());
		
		inventory.setPricePerAmount();
		inventoryRepository.save(inventory);
		
		return "redirect:/inventories/" + idIngredient;
	}
	
	@GetMapping("/register/{idIngredient}")
	public String showInventoryRegisterForm(@PathVariable("idIngredient") Long idIngredient,
											@AuthenticationPrincipal SecurityUser securityUser,
											Model model) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		Ingredient ingredient = ingredientRepository.findById(idIngredient).get();
		
		if(ingredient.getIngredientOwner().getId() != userId) {
			return "error/accessDenied";
		}
		
		Inventory inventory = new Inventory();
		inventory.setIngredient(ingredient);
		
		model.addAttribute("inventory", inventory);
		
		return "inventories/inventoryForm";
	}
	
	@PostMapping("/register/{idIngredient}")
	public String registerInventory(@PathVariable("idIngredient") Long idIngredient,
									@Valid Inventory inventory,
									BindingResult bindingResult,
									@AuthenticationPrincipal SecurityUser securityUser,
									Model model) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		Ingredient ingredient = ingredientRepository.findById(idIngredient).get();
		
		if(ingredient.getIngredientOwner().getId() != userId) {
			return "error/accessDenied";
		} else if(bindingResult.hasErrors()) {
			model.addAttribute("inventory", inventory);
			return "inventories/inventoryForm";
		}
		
		inventory.setPricePerAmount();
		inventoryRepository.save(inventory);
		
		return "redirect:/inventories/" + idIngredient;
	}
	
	@GetMapping("/edit/{idInventory}")
	public String showInventoryUpdateForm(@PathVariable("idInventory") Long idInventory,
											@AuthenticationPrincipal SecurityUser securityUser,
											Model model) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		Ingredient ingredient = inventoryRepository.findById(idInventory).get().getIngredient();
		
		if(ingredient.getIngredientOwner().getId() != userId) {
			return "error/accessDenied";
		}
		
		model.addAttribute("usedInventoryExists", usedInventoryRepository.existsByInventoryEntryId(idInventory));
		
		Inventory inventory = inventoryRepository.findById(idInventory).get();
		model.addAttribute("inventory", inventory);
		
		return "inventories/inventoryUpdateForm";
	}
	
	@PostMapping("/edit/{idInventory}")
	public String updateInventory(@PathVariable("idInventory") Long idInventory,
									@Valid Inventory inventory,
									BindingResult bindingResult,
									@AuthenticationPrincipal SecurityUser securityUser,
									Model model) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		Ingredient ingredient = inventoryRepository.findById(idInventory).get().getIngredient();
		
		if(ingredient.getIngredientOwner().getId() != userId) {
			return "error/accessDenied";
		} else if(bindingResult.hasErrors()) {
			
			model.addAttribute("inventory", inventory);
			
			return "inventories/inventoryUpdateForm";
		}
		
		String idIngredient = Long.toString(inventory.getIngredient().getId());
		
		inventory.setPricePerAmount();
		inventoryRepository.save(inventory);
		
		return "redirect:/inventories/" + idIngredient;
	}
	
	@GetMapping("/delete/{idInventory}")
	public String deleteInventory(@PathVariable("idInventory") Long idInventory,
									@AuthenticationPrincipal SecurityUser securityUser) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		Ingredient ingredient = inventoryRepository.findById(idInventory).get().getIngredient();
		
		if(ingredient.getIngredientOwner().getId() != userId) {
			return "error/accessDenied";
		}
		
		inventoryRepository.deleteById(idInventory);
		
		String idIngredient = Long.toString(inventoryRepository.findById(idInventory).get().getIngredient().getId());
		
		return "redirect:/inventories/" + idIngredient;
	}
	
	@GetMapping("/deleteExpired/{ingredientId}")
	public String deleteExpiredInventory(@PathVariable("ingredientId") Long ingredientId,
											@AuthenticationPrincipal SecurityUser securityUser) {
		
		Long userId = userRepository.findByUsername(securityUser.getUsername()).get().getId();
		Ingredient ingredient = ingredientRepository.findById(ingredientId).get();
		
		if(ingredient.getIngredientOwner().getId() != userId) {
			return "error/accessDenied";
		}
		
		inventoryService.deleteExpiredInventories(ingredientId);
		
		return "redirect:/inventories/" + ingredientId;
	}
	
}
