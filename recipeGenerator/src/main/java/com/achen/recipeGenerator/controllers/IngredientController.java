package com.achen.recipeGenerator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.achen.recipeGenerator.service.IngredientService;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

	@Autowired
	private IngredientService ingredientService;
	
	@GetMapping("/getIngredients/{ingredientName}")
	@ResponseBody
	public ResponseEntity<?> getIngredients(@PathVariable("ingredientName") String name) {
		return ingredientService.getIngredientByName(name);
	}
	
	@RequestMapping(value="/addIngredientText/{ingredientName}", method = RequestMethod.POST)
	public ResponseEntity<?> addIngredientsText(@PathVariable("ingredientName") String name) {
		return ingredientService.addIngredientText(name, 123);
	}
}
