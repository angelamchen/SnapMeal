package com.achen.recipeGenerator.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("recipeService")
public interface RecipeService {
	public ResponseEntity<?> getRecipeFromTitle(String title);
	
	public ResponseEntity<?> getAvailableRecipes(String userId);
}
