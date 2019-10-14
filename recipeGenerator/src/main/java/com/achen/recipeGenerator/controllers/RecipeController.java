package com.achen.recipeGenerator.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.achen.recipeGenerator.models.dto.RecipeDto;
import com.achen.recipeGenerator.service.RecipeService;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
	@Autowired
	private RecipeService recipeService;

	@CrossOrigin
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAvailableRecipes(@PathVariable String userId) {
		try {
			List<RecipeDto> recipes = recipeService.getAvailableRecipes(userId);
			
			return ResponseEntity.status(HttpStatus.OK).body(recipes);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseEntity<?> cleanRecipes() {
		try {
			int deletedRecipes = recipeService.cleanRecipes();
			return ResponseEntity.status(HttpStatus.OK)
					.body(String.format("%s recipes succesfully deleted", deletedRecipes));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
	}
}
