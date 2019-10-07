package com.achen.recipeGenerator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.achen.recipeGenerator.models.Ingredient;
import com.achen.recipeGenerator.models.Recipe;
import com.achen.recipeGenerator.repositories.RecipeRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service("recipeService")
public class RecipeServiceImpl implements RecipeService {
	private Gson gson = new GsonBuilder().create();
	
	@Autowired
	RecipeRepo recipeRepo;
	
	@Autowired
	IngredientService ingredientService;

	@Override
	public ResponseEntity<?> getRecipeFromTitle(String title) {
		List<Recipe> recipes = recipeRepo.findAllByTitle(title);
		
		if (recipes.size() != 1) {
			return new ResponseEntity<>(String.format("Could not find: %s", title), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(gson.toJson(recipes.get(0)), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getAvailableRecipes(String userId) {
		ResponseEntity<?> response = ingredientService.getAllIngredientsByUser(userId);
		return response;
	}

}
