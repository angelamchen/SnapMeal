package com.achen.recipeGenerator.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.achen.recipeGenerator.models.ImageRequestDto;
import com.achen.recipeGenerator.models.Ingredient;
import com.achen.recipeGenerator.repositories.IngredientRepo;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import okhttp3.OkHttpClient;

@Service("ingredientService")
public class IngredientServiceImpl implements IngredientService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IngredientServiceImpl.class);

	@Autowired
	IngredientRepo ingredientRepo;
	
	@Autowired
	ClarifaiRecognitionService clarifaiClient;
	
	
	@Override
	public Ingredient saveNewIngredient(String ingredientName, String userId) {
			List<Ingredient> ingredient = getIngredientByNameAndUser(ingredientName.toLowerCase(), userId);
			
			if (!ingredient.isEmpty()) {
				// TODO: create own exception and throw personalized exception
			}
			
			Date dateobj = new Date();

			Ingredient newIngredient = new Ingredient();
			newIngredient.setIngredientName(ingredientName.toLowerCase());
			newIngredient.setUserId(userId);
			newIngredient.setDate(dateobj);

			ingredientRepo.save(newIngredient);

			return newIngredient;
	}
	
	@Override
	public List<Ingredient> saveIngredientFromImage(ImageRequestDto imageProp, String userId) {
		byte[] imageBytes = Base64.getDecoder().decode(imageProp.getImageString());
		
		List<Concept> retrievedIngredients = clarifaiClient.sendImageToClarifaiSync(imageBytes);
		
		List<Ingredient> validIngredients = new ArrayList<>();
		
		for (Concept ingredient : retrievedIngredients) {
			if (ingredient.value() > 0.95) {
				List<Ingredient> existingIngredient = getIngredientByNameAndUser(ingredient.name().toLowerCase(), userId);
				
				if (!existingIngredient.isEmpty()) {
					continue;
				}
				
				Ingredient newIngredient = saveNewIngredient(ingredient.name(), userId);
				validIngredients.add(newIngredient);
			}
		}
		
		return validIngredients;
	}
	
	@Override
	public Ingredient getIngredientByName(String name) {
		List<Ingredient> ingredients = ingredientRepo.findAllByName(name);

		if (ingredients.size() != 1) {
			// error that none or too much are found
		}
		return ingredients.get(0);
	}
	
	@Override
	public List<Ingredient> getAllIngredientsByUser(String userId) {
			List<Ingredient> ingredients = ingredientRepo.findAllByUserId(userId);
			return ingredients;
	}
	
	@Override
	public List<Ingredient> getIngredientByNameAndUser(String ingredientName, String userId) {
		return ingredientRepo.findByIngredientNameAndUserId(ingredientName, userId);
	}

	@Override
	public Ingredient removeIngredredientByName(String ingredientName) {
		Ingredient ingredient = getIngredientByName(ingredientName.toLowerCase());
		ingredientRepo.delete(ingredient);
		
		return ingredient;
	}
}
