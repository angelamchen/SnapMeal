package com.achen.recipeGenerator.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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
		// TODO: create a new Recipe object with important details, e.g. percentage
		// match and stuff
		List<Recipe> matchedRecipes = new ArrayList<>();
		List<Ingredient> ingredients = ingredientService.getAllIngredientsByUser(userId);

		// Put ingredients into a hashSet for quicker lookup
		HashSet<String> ingredientsSet = ingredients.stream().map(i -> i.getIngredientName())
				.collect(Collectors.toCollection(HashSet::new));

		for (Ingredient ingredient : ingredients) {
			// TODO: Find all recipes containing that ingredient
			List<Recipe> recipesWithIngredient = recipeRepo.findRecipesContainingIngredient(ingredient.getIngredientName());

			for (Recipe recipe : recipesWithIngredient) {
				if (matchedRecipes.contains(recipe)) {
					continue;
				}

				List<String> recipeIngredients = recipe.getCategories();

				int totalIngredients = recipeIngredients.size();
				int matchedIngredients = 0;

				for (String ingredientCategory : recipeIngredients) {
					if (ingredientsSet.contains(ingredientCategory)) {
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
}
