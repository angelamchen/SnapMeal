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
import com.achen.recipeGenerator.models.Recipe;
import com.achen.recipeGenerator.service.RecipeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
	private Gson gson = new GsonBuilder().create();
	
	@Autowired
	private RecipeService recipeService;
	
	@RequestMapping(value="/getRecipeByTitle", method = RequestMethod.POST)
	public ResponseEntity<?> getRecipeByTitle(@RequestBody ImageRequestDto recipeTitle) {
		return recipeService.getRecipeFromTitle(recipeTitle.getImageString());
	}
	
	@RequestMapping(value="/getAvailableRecipes/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAvailableRecipes(@PathVariable String userId) {
		try {
			List<Recipe> recipes = recipeService.getAvailableRecipes(userId);
			return new ResponseEntity<>(gson.toJson(recipes.get(0)), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(String.format("Could not find: %s", userId), HttpStatus.BAD_REQUEST);
		}
	}

}