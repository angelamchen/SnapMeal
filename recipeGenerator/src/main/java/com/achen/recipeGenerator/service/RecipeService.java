package com.achen.recipeGenerator.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.achen.recipeGenerator.models.Recipe;

@Service("recipeService")
public interface RecipeService {
	public ResponseEntity<?> getRecipeFromTitle(String title);
	
	public List<Recipe> getAvailableRecipes(String userId);
}
