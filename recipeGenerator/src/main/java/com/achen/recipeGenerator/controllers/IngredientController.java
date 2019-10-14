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

	@RequestMapping(value = "/ingredient", method = RequestMethod.GET)
	public ResponseEntity<?> getIngredientByName(@RequestParam String ingredientName) {
		try {
			Ingredient ingredient = ingredientService.getIngredientByName(ingredientName);
			return new ResponseEntity<Ingredient>(ingredient, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/UserIngredient", method = RequestMethod.GET)
	public ResponseEntity<?> getAllIngredientsByUser(@RequestParam String userId) {
		try {
			List<Ingredient> ingredients = ingredientService.getAllIngredientsByUser(userId);
			return new ResponseEntity<List<Ingredient>>(ingredients, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/{ingredientName}/{userId}", method = RequestMethod.POST)
	public ResponseEntity<?> addIngredientsText(@PathVariable("ingredientName") String ingredientName,
			@PathVariable("userId") String userId) {
		try {
			Ingredient ingredient = ingredientService.saveNewIngredient(ingredientName, userId);
			return new ResponseEntity<Ingredient>(ingredient, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/{userId}", method = RequestMethod.POST)
	public ResponseEntity<?> addIngredientImage(@RequestBody ImageRequestDto imageProp, @PathVariable String userId) {
		try {
			List<Ingredient> ingredients = ingredientService.saveIngredientFromImage(imageProp, userId);
			return new ResponseEntity<List<Ingredient>>(ingredients, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/{ingredientName}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeIngredredientByName(@PathVariable String ingredientName) {
		try {
			Ingredient ingredient = ingredientService.removeIngredredientByName(ingredientName);
			return new ResponseEntity<Ingredient>(ingredient, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
