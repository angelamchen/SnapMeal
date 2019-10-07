package com.achen.recipeGenerator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.achen.recipeGenerator.models.ImageRequestDto;
import com.achen.recipeGenerator.service.RecipeService;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
	@Autowired
	private RecipeService recipeService;
	
	@RequestMapping(value="/getRecipeByTitle", method = RequestMethod.POST)
	public ResponseEntity<?> getRecipeByTitle(@RequestBody ImageRequestDto recipeTitle) {
		return recipeService.getRecipeFromTitle(recipeTitle.getImageString());
	}
	
	@RequestMapping(value="/getAvailableRecipes/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAvailableRecipes(@PathVariable String userId) {
		return recipeService.getAvailableRecipes(userId);
	}

}
