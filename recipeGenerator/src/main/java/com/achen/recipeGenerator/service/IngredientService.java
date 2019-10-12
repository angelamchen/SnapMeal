package com.achen.recipeGenerator.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.achen.recipeGenerator.models.ImageRequestDto;
import com.achen.recipeGenerator.models.Ingredient;

@Service("ingredientService")
public interface IngredientService {

	public ResponseEntity<?> getIngredientByName(String ingredientName);

	public List<Ingredient> getAllIngredientsByUser(String userId);
	
	public ResponseEntity<?> addIngredientFromText(String ingredientName, String userId);

	public ResponseEntity<?> addIngredientFromImage(ImageRequestDto imageProp, String userId);
}
