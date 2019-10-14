package com.achen.recipeGenerator.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.achen.recipeGenerator.models.Ingredient;
import com.achen.recipeGenerator.models.Recipe;
import com.achen.recipeGenerator.models.Dto.RecipeDto;
import com.achen.recipeGenerator.repositories.RecipeRepo;

@Service("recipeService")
public class RecipeServiceImpl implements RecipeService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RecipeServiceImpl.class);

	@Autowired
	RecipeRepo recipeRepo;

	@Autowired
	IngredientService ingredientService;

	/**
	 * deletes all the recipes with null entries, or recipes with rating under 4
	 * 
	 * @returns The number of recipes that have been deleted
	 */
	@Override
	public int cleanRecipes() {
		List<Recipe> recipesToDelete = recipeRepo.deleteBadRecipes();

		LOGGER.info("cleanRecipes() | Total recipes found: {}. Beginning to delete", recipesToDelete.size());
		for (Recipe recipe : recipesToDelete) {
			recipeRepo.deleteRecipeByTitle(recipe.getTitle());
		}
		return recipesToDelete.size();
	}

	/**
	 * finds all the recipes that a user could potentially make based on their
	 * ingredients
	 * 
	 * @param userId the userId of the user to find recipes for
	 * 
	 * @returns A list of all the recipes a user could make
	 */
	@Override
	public List<RecipeDto> getAvailableRecipes(String userId) {
		List<RecipeDto> matchedRecipes = new ArrayList<>();
		HashSet<String> matchedRecipeNames = new HashSet<>();

		List<Ingredient> userIngredients = ingredientService.getAllIngredientsByUser(userId);

		for (Ingredient ingredient : userIngredients) {
			// Get all the recipes that contain the current ingredient
			List<Recipe> recipesWithIngredient = recipeRepo.findByIngredientsIn(ingredient.getIngredientName());

			if (recipesWithIngredient == null || recipesWithIngredient.isEmpty()) {
				continue;
			}

			LOGGER.info("getAvailableRecipes() | Total recipes for {} found: {}", ingredient.getIngredientName(),
					recipesWithIngredient.size());

			// For each recipe, if the user has at least 50% of required ingredients, add it
			// to matchedRecipes list
			for (Recipe recipe : recipesWithIngredient) {
				if (matchedRecipeNames.contains(recipe.getTitle())) {
					LOGGER.info("getAvailableRecipes() | Recipe: {} has already been found, searching for new recipes",
							recipe.getTitle());
					continue;
				}

				double percentageMatch = findMatchPercentage(recipe.getIngredients(), userIngredients);

				if (percentageMatch > 0.5) {
					RecipeDto matchedRecipe = new RecipeDto();
					matchedRecipe.setTitle(recipe.getTitle());
					matchedRecipe.setDirections(recipe.getDirections());
					matchedRecipe.setIngredients(recipe.getIngredients());
					matchedRecipe.setPercentageMatch(percentageMatch);

					matchedRecipes.add(matchedRecipe);
					matchedRecipeNames.add(recipe.getTitle());
				}
			}
		}
		return matchedRecipes;
	}

	/**
	 * finds the percentage of ingredients in the users pantry that matches with the
	 * recipe ingredients
	 * 
	 * @param recipeIngredients List of all the ingredients required for the recipe
	 * @param userIngredients   List of all the ingredients the user has
	 * 
	 * @returns a double representing the percentage of ingredients for the recipe
	 *          that the user has
	 */
	private double findMatchPercentage(List<String> recipeIngredients, List<Ingredient> userIngredients) {
		int matchedIngredients = 0;
		int totalIngredients = recipeIngredients.size();

		for (String recipeIngredient : recipeIngredients) {
			if (doesUserHaveIngredient(recipeIngredient, userIngredients)) {
				matchedIngredients++;
			}
		}

		return matchedIngredients / totalIngredients;
	}

	/**
	 * Determines if the user has the specified ingredient passed in
	 * 
	 * @param recipeIngredient The ingredient in the recipe
	 * @param userIngredients   List of all the ingredients the user has
	 * 
	 * @returns true if the user has the ingredient, false otherwise
	 */
	private boolean doesUserHaveIngredient(String recipeIngredient, List<Ingredient> userIngredients) {
		for (Ingredient ingredient : userIngredients) {
			if (recipeIngredient.contains(ingredient.getIngredientName())) {
				return true;
			}
		}
		return false;
	}
}
