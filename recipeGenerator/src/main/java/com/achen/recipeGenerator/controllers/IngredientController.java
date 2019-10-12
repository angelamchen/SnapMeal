package com.achen.recipeGenerator.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.achen.recipeGenerator.models.ImageRequestDto;
import com.achen.recipeGenerator.models.Ingredient;
import com.achen.recipeGenerator.service.IngredientService;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
	//TODO: better more informative response Entities
	@Autowired
	private IngredientService ingredientService;
	
	@RequestMapping(value="/getIngredientByName/{ingredientName}", method = RequestMethod.GET)
	public ResponseEntity<?> getIngredientByName(@PathVariable String ingredientName) {
		return ingredientService.getIngredientByName(ingredientName);
	}
	
	@RequestMapping(value="/getAllIngredientsByUser/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllIngredientsByUser(@PathVariable String userId) {
		List<Ingredient> ingredients = ingredientService.getAllIngredientsByUser(userId);
		return new ResponseEntity<>(String.format("Ingredient %s saved", ingredients), HttpStatus.OK);
	}
	
	@RequestMapping(value="/addIngredientFromText/{ingredientName}/{userId}", method = RequestMethod.POST)
	public ResponseEntity<?> addIngredientsText(@PathVariable("ingredientName") String ingredientName, @PathVariable("userId") String userId) {
		return ingredientService.addIngredientFromText(ingredientName, userId);
	}
	
	@RequestMapping(value="/addIngredientFromImage/{userId}", method = RequestMethod.POST)
	public ResponseEntity<?> addIngredientImage(@RequestBody ImageRequestDto imageProp, @PathVariable String userId) {
		return ingredientService.addIngredientFromImage(imageProp, userId);
	}
}