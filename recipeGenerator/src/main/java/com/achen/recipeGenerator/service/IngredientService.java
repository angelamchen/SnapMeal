package com.achen.recipeGenerator.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.achen.recipeGenerator.models.Ingredient;
import com.achen.recipeGenerator.models.dto.ImageRequestDto;

@Service("ingredientService")
public interface IngredientService {
	public Ingredient saveNewIngredient(String ingredientName, String userId);

	public List<Ingredient> saveIngredientFromImage(ImageRequestDto imageProp, String userId);

	public List<Ingredient> getAllIngredientsByUser(String userId);
	
	public Ingredient removeIngredredientByNameAndUser(String ingredientName, String userId) throws Exception;
	
	public Boolean doesIngredientExist(String ingredientName, String userId);
}
