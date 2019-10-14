package com.achen.recipeGenerator.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.achen.recipeGenerator.models.ImageRequestDto;
import com.achen.recipeGenerator.models.Ingredient;
import com.achen.recipeGenerator.service.IngredientService;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
	@Autowired
	private IngredientService ingredientService;
	
	@RequestMapping(value="/ingredient", method = RequestMethod.GET)
	public ResponseEntity<?> getIngredientByName(@RequestParam String ingredientName) {
		return ingredientService.getIngredientByName(ingredientName);
	}
	
	@CrossOrigin
	@RequestMapping(value="/UserIngredient", method = RequestMethod.GET)
	public ResponseEntity<?> getAllIngredientsByUser(@RequestParam String userId) {
		List<Ingredient> ingredients = ingredientService.getAllIngredientsByUser(userId);
		return new ResponseEntity<>(ingredients, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value="/{ingredientName}/{userId}", method = RequestMethod.POST)
	public ResponseEntity<?> addIngredientsText(@PathVariable("ingredientName") String ingredientName, @PathVariable("userId") String userId) {
		return ingredientService.addIngredientFromText(ingredientName, userId);
	}
	
	@CrossOrigin
	@RequestMapping(value="/{userId}", method = RequestMethod.POST)
	public ResponseEntity<?> addIngredientImage(@RequestBody ImageRequestDto imageProp, @PathVariable String userId) {
		return ingredientService.addIngredientFromImage(imageProp, userId);
	}
	
	@CrossOrigin
	@RequestMapping(value="/{ingredientName}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeIngredredientByName(@PathVariable String ingredientName) {
		return ingredientService.removeIngredredientByName(ingredientName);
	}
}
