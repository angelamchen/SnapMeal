package com.achen.recipeGenerator.service;

import java.util.ArrayList;
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

	// TODO: Maybe should not return response entities, rather let the controllers
	// handle HTTP requests and services
	// handle bus logic
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
	public List<Recipe> getAvailableRecipes(String userId) {
		List<Recipe> matchedRecipes = new ArrayList<>();
		List<Ingredient> userIngredients = ingredientService.getAllIngredientsByUser(userId);

		for (Ingredient ingredient : userIngredients) {
			List<Recipe> recipesWithIngredient = new ArrayList<>();
			
			try {
				recipesWithIngredient = recipeRepo.findRecipesContainingIngredient(ingredient.getIngredientName());
			} catch (Exception e) {
				return recipesWithIngredient;
			}
		
			if (recipesWithIngredient == null || recipesWithIngredient.isEmpty()) {
				continue;
			}

			for (Recipe recipe : recipesWithIngredient) {
				if (matchedRecipes.contains(recipe)) {
					continue;
				}

				List<String> recipeIngredients = recipe.getIngredients();

				int totalIngredients = recipeIngredients.size();
				int matchedIngredients = 0;

				for (String recipeIngredient : recipeIngredients) {
					if (doesRecipeContainIngredient(recipeIngredient, userIngredients)) {
						matchedIngredients++;
					}
				}

				float percentageMatch = matchedIngredients / totalIngredients;
				if (percentageMatch > 0.8) {
					matchedRecipes.add(recipe);
				}
			}
		}
		// TODO: create a new object that contains the ingredient matched percentage
		return matchedRecipes;
	}
	
	private boolean doesRecipeContainIngredient(String recipeIngredient, List<Ingredient> userIngredients) {
		for (Ingredient ingredient : userIngredients) {
			if (recipeIngredient.contains(ingredient.getIngredientName())) {
				return true;
			}
		}
		
		return false;
	}
}
