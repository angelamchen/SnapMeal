package com.achen.recipeGenerator.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.achen.recipeGenerator.models.ImageRequestDto;
import com.achen.recipeGenerator.models.Ingredient;

@Service("ingredientService")
public interface IngredientService {

	public Ingredient getIngredientByName(String ingredientName);

	public List<Ingredient> getAllIngredientsByUser(String userId);
	
	public List<Ingredient> getIngredientByNameAndUser(String ingredientName, String userId);
	
	public Ingredient saveNewIngredient(String ingredientName, String userId);

	public List<Ingredient> saveIngredientFromImage(ImageRequestDto imageProp, String userId);

	public Ingredient removeIngredredientByName(String ingredientName);
}
