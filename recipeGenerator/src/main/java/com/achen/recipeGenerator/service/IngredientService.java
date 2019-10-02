package com.achen.recipeGenerator.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("ingredientService")
public interface IngredientService {

	public ResponseEntity<?> addIngredientText(String ingredientName, String userId);

	public ResponseEntity<?> getIngredientByName(String ingredientName);

	public ResponseEntity<?> getAllIngredientsByUser(String userId);

	public ResponseEntity<?> addIngredientfromImage();
	
}
