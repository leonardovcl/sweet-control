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
import org.springframework.web.bind.annotation.RequestParam;

import dev.leonardovcl.sweetcontrol.model.Ingredient;
import dev.leonardovcl.sweetcontrol.model.repository.IngredientRepository;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

	@Autowired
	private IngredientRepository ingredientRepository;
	
	@GetMapping
	public String showIngredients(Model model) {
		model.addAttribute("ingredientList", ingredientRepository.findAll());
		return "ingredients";
	}
	
	@PostMapping
	public String showIngredientsWithFilter(@RequestParam("nameLike") String nameLike, Model model) {
		System.out.println("Aqui: " + nameLike);
		model.addAttribute("ingredientList", ingredientRepository.findByNameContainingIgnoreCase(nameLike));
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