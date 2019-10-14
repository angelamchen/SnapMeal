package com.achen.recipeGenerator.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.achen.recipeGenerator.models.Ingredient;
import com.achen.recipeGenerator.models.Recipe;
import com.achen.recipeGenerator.models.Dto.RecipeDto;
import com.achen.recipeGenerator.repositories.RecipeRepo;

@Service("recipeService")
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	RecipeRepo recipeRepo;

	@Autowired
	IngredientService ingredientService;

	// TODO: make this more graceful
	@Override
	public String deleteStuff() {
		List<Recipe> recipe = recipeRepo.deleteBadRecipes();
		System.out.println(recipe.size());

		for (Recipe recipe2 : recipe) {
			recipeRepo.deleteRecipeByTitle(recipe2.getTitle());
		}

		return "Success";
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

			for (Recipe recipe : recipesWithIngredient) {
				if (matchedRecipeNames.contains(recipe.getTitle())) {
					System.out.println("Recipe has already been found, searching for new recipes");
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

				double percentageMatch = matchedIngredients / totalIngredients;
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

	private boolean doesRecipeContainIngredient(String recipeIngredient, List<Ingredient> userIngredients) {
		for (Ingredient ingredient : userIngredients) {
			if (recipeIngredient.contains(ingredient.getIngredientName())) {
				return true;
			}
		}

		return false;
	}
}
