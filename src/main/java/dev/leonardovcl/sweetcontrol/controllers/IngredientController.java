package dev.leonardovcl.sweetcontrol.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.leonardovcl.sweetcontrol.model.Ingredient;
import dev.leonardovcl.sweetcontrol.model.repository.IngredientRepository;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

	@Autowired
	private IngredientRepository ingredientRepository;
	
	@GetMapping
	public String showIngredients(
			@RequestParam(value = "page", required = false,  defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "5") int size,
			@RequestParam(value = "nameLike", required = false, defaultValue = "") String nameLike,
			@RequestParam(value = "idFilter", required = false) Long idFilter,
			Model model) {
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
	
		Page<Ingredient> ingredientList;
		
		List<Ingredient> ingredientArrayList = new ArrayList<>();
		
		if (idFilter != null) {
			Ingredient ingredientById = ingredientRepository.findById(idFilter).isPresent() ? ingredientRepository.findById(idFilter).get() : null;
			
			if (ingredientById != null) {
				ingredientArrayList.add(ingredientById);
			}
			
			ingredientList = new PageImpl<>(ingredientArrayList, pageable, ingredientArrayList.size());
			
		} else if (!nameLike.isBlank()) {
			ingredientList = ingredientRepository.findByNameContainingIgnoreCase(nameLike, pageable);
		} else {
			ingredientList = ingredientRepository.findAll(pageable);
		}
		
		model.addAttribute("ingredientList", ingredientList);
		
		model.addAttribute("idFilter", idFilter);
		model.addAttribute("nameLike", nameLike);
		
		return "ingredients";
	}
	
	@GetMapping("/register")
	public String showIngredientRegisterForm(Ingredient ingredient) {
		return "ingredientForm";
	}
	
	@PostMapping("/register")
	public String registerIngredient(@Valid Ingredient ingredient) {
		ingredientRepository.save(ingredient);
		return "redirect:/ingredients";
	}
	
	@GetMapping("/edit/{idIngredient}")
	public String showIngredientUpdateForm(@PathVariable("idIngredient") Long idIngredient, Model model) {
		Optional<Ingredient> ingredient = ingredientRepository.findById(idIngredient);
		model.addAttribute("ingredient", ingredient);
		return "ingredientUpdateForm";
	}
	
	@PostMapping("/edit/{idIngredient}")
	public String updateIngredient(@PathVariable("idIngredient") Long idIngredient, @Valid Ingredient ingredient) {
		ingredientRepository.save(ingredient);
		return "redirect:/ingredients";
	}
	
	@GetMapping("/delete/{idIngredient}")
	public String deleteIngredient(@PathVariable("idIngredient") Long idIngredient) {
		ingredientRepository.deleteById(idIngredient);
		return "redirect:/ingredients";
	}
	
}
