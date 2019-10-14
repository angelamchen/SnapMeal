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

import com.achen.recipeGenerator.models.Ingredient;
import com.achen.recipeGenerator.models.dto.ImageRequestDto;
import com.achen.recipeGenerator.service.IngredientService;

/**
 * 
 * Handles request mapping for Ingredient related API calls
 *
 */
@RestController
@RequestMapping("/ingredients")
public class IngredientController {
	@Autowired
	private IngredientService ingredientService;

	@CrossOrigin
	@RequestMapping(value = "/UserIngredient", method = RequestMethod.GET)
	public ResponseEntity<?> getAllIngredientsByUser(@RequestParam String userId) {
		try {
			List<Ingredient> ingredients = ingredientService.getAllIngredientsByUser(userId);
			return ResponseEntity.status(HttpStatus.OK)
					.body(ingredients);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/{ingredientName}/{userId}", method = RequestMethod.POST)
	public ResponseEntity<?> addIngredientsText(@PathVariable("ingredientName") String ingredientName,
			@PathVariable("userId") String userId) {
		try {
			if (ingredientService.doesIngredientExist(ingredientName, userId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(String.format("Ingredient: %s for user: %s already exists", ingredientName, userId));
			}
			
			Ingredient ingredient = ingredientService.saveNewIngredient(ingredientName, userId);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(ingredient);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/{userId}", method = RequestMethod.POST)
	public ResponseEntity<?> addIngredientImage(@RequestBody ImageRequestDto imageProp, @PathVariable String userId) {
		try {
			List<Ingredient> ingredients = ingredientService.saveIngredientFromImage(imageProp, userId);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(ingredients);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/{ingredientName}/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeIngredredientByName(@PathVariable String ingredientName, @PathVariable String userId) {
		try {
			Ingredient ingredient = ingredientService.removeIngredredientByNameAndUser(ingredientName, userId);
			return ResponseEntity.status(HttpStatus.OK)
					.body(ingredient);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		}
	}
}
