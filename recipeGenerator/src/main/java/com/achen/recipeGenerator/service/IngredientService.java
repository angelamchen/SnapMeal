package com.achen.recipeGenerator.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("ingredientService")
public interface IngredientService {

	public ResponseEntity<?> addIngredientText(String ingredientName, int userId);

	public ResponseEntity<?> getIngredientByName(String name);
}
