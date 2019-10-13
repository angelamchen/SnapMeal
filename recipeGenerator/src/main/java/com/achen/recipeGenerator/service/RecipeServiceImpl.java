package com.achen.recipeGenerator.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.achen.recipeGenerator.models.Ingredient;
import com.achen.recipeGenerator.models.Recipe;
import com.achen.recipeGenerator.models.RecipeDto;
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
	public String deleteStuff() {
		try {
			System.out.println("I ran");
			List<Recipe> recipe = recipeRepo.deleteBadRecipes();
			System.out.println(recipe.size());

			for (Recipe recipe2 : recipe) {
				recipeRepo.deleteRecipeByTitle(recipe2.getTitle());
			}

			return "Success";
		} catch (Exception e) {
			return "I failed" + e.toString();
		}

	}

	@Override
	public List<RecipeDto> getAvailableRecipes(String userId) {
		List<RecipeDto> matchedRecipes = new ArrayList<>();
		HashSet<String> matchedRecipeNames = new HashSet<>();
		List<Ingredient> userIngredients = ingredientService.getAllIngredientsByUser(userId);

		for (Ingredient ingredient : userIngredients) {
			List<Recipe> recipesWithIngredient = new ArrayList<>();

			System.out.println(String.format("Finding recipes for ingredient: %s", ingredient.getIngredientName()));
			recipesWithIngredient = recipeRepo.findByIngredientsIn(ingredient.getIngredientName());

			if (recipesWithIngredient == null || recipesWithIngredient.isEmpty()) {
				continue;
			}

			System.out.println(String.format("Total recipes found: %s", recipesWithIngredient.size()));

//			for (Recipe recipe : recipesWithIngredient) {
//				if (matchedRecipeNames.contains(recipe.getTitle())) {
//					System.out.println("Recipe has already been found, searching for new recipes");
//					continue;
//				}
//
//				List<String> recipeIngredients = recipe.getIngredients();
//
//				int totalIngredients = recipeIngredients.size();
//				System.out.println(String.format("Total ingredients found: %s", recipe.getTitle()));
//				int matchedIngredients = 0;
//
//				for (String recipeIngredient : recipeIngredients) {
//					if (doesRecipeContainIngredient(recipeIngredient, userIngredients)) {
//						matchedIngredients++;
//					}
//				}
//
//				double percentageMatch = matchedIngredients / totalIngredients;
//				if (percentageMatch > 0) {
//					RecipeDto matchedRecipe = new RecipeDto();
//					matchedRecipe.setTitle(recipe.getTitle());
//					matchedRecipe.setDirections(recipe.getDirections());
//					matchedRecipe.setIngredients(recipe.getIngredients());
//					matchedRecipe.setPercentageMatch(percentageMatch);
//
//					matchedRecipes.add(matchedRecipe);
//					matchedRecipeNames.add(recipe.getTitle());
//				}
//			}
			RecipeDto matchedRecipe = new RecipeDto();
			matchedRecipe.setDirections(recipesWithIngredient.get(0).getDirections());
			matchedRecipe.setTitle(recipesWithIngredient.get(0).getTitle());
			matchedRecipes.add(matchedRecipe);
		}
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
