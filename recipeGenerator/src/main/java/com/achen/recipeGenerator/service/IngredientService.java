package com.achen.recipeGenerator.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.achen.recipeGenerator.models.ImageRequestDto;

@Service("ingredientService")
public interface IngredientService {

	public ResponseEntity<?> getIngredientByName(String ingredientName);

	public ResponseEntity<?> getAllIngredientsByUser(String userId);
	
	public ResponseEntity<?> addIngredientFromText(String ingredientName, String userId);

	public ResponseEntity<?> addIngredientFromImage(ImageRequestDto imageProp);
}
