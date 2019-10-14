package com.achen.recipeGenerator.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.achen.recipeGenerator.models.Ingredient;
import com.achen.recipeGenerator.models.Dto.ImageRequestDto;
import com.achen.recipeGenerator.repositories.IngredientRepo;

import clarifai2.dto.prediction.Concept;

@Service("ingredientService")
public class IngredientServiceImpl implements IngredientService {

	@Autowired
	IngredientRepo ingredientRepo;

	@Autowired
	ClarifaiRecognitionService clarifaiClient;

	/**
	 * Saves ingredients to the ingredient database, given that ingredient does not
	 * already exists for user
	 * 
	 * @param ingredientName Name of the ingredient to save
	 * @param userId         userId of the user the ingredient belongs to
	 * @return saved new ingredient object
	 */
	@Override
	public Ingredient saveNewIngredient(String ingredientName, String userId) {
		Date dateobj = new Date();

		Ingredient newIngredient = new Ingredient();
		newIngredient.setIngredientName(ingredientName.toLowerCase());
		newIngredient.setUserId(userId);
		newIngredient.setDate(dateobj);

		ingredientRepo.save(newIngredient);

		return newIngredient;
	}

	/**
	 * Saves ingredients to the ingredient database from base64 image string, given
	 * that the ingredients do not already exists for user
	 * 
	 * @param imageProp base64 byte string of image to analyze
	 * @param userId    userId of the user the ingredients in image belong to
	 * @return saved new ingredient object
	 */
	@Override
	public List<Ingredient> saveIngredientFromImage(ImageRequestDto imageProp, String userId) {
		final double MATCH_PERCENTAGE = 0.95;
		List<Ingredient> validIngredients = new ArrayList<>();

		byte[] imageBytes = Base64.getDecoder().decode(imageProp.getImageString());

		List<Concept> retrievedIngredients = clarifaiClient.sendImageToClarifaiSync(imageBytes);

		for (Concept ingredient : retrievedIngredients) {
			// If the match is greater than 0.95 and ingredient does not already exist, add to db
			if (ingredient.value() > MATCH_PERCENTAGE && !doesIngredientExist(ingredient.name(), userId)) {
				Ingredient newIngredient = saveNewIngredient(ingredient.name(), userId);
				validIngredients.add(newIngredient);
			}
		}

		return validIngredients;
	}

	/**
	 * Retrieves a users ingredients from the ingredient database
	 * 
	 * @param userId string id of the user ingredients belong to
	 * @return list of all the ingredients belong to the user
	 */
	@Override
	public List<Ingredient> getAllIngredientsByUser(String userId) {
		return ingredientRepo.findAllByUserId(userId);
	}
		
	/**
	 * Removes a users ingredient in the ingredient database
	 * 
	 * @param ingredientName name of the ingredient to be removed
	 * @param userId id of the user ingredients belong to
	 * @return ingredient that was deleted
	 * @throws Exception if ingredient does not exist
	 */
	@Override
	public Ingredient removeIngredredientByNameAndUser(String ingredientName, String userId) throws Exception {
		List<Ingredient> ingredients = ingredientRepo.findByIngredientNameAndUserId(ingredientName, userId);
		
		if (ingredients.isEmpty()) {
			throw new Exception(String.format("Ingredient: %s for user: %s does not exist", ingredientName, userId));
		}
		
		ingredientRepo.delete(ingredients.get(0));
		return ingredients.get(0);
	}

	/**
	 * Determines if a user ingredient exists
	 * 
	 * @param ingredientName name of the ingredient
	 * @param userId id of the user ingredient belongs to
	 * @return true, if only one ingredient exists
	 * @return false, if ingredient does not exist, or if many exist
	 */
	@Override
	public Boolean doesIngredientExist(String ingredientName, String userId) {
		List<Ingredient> ingredients = ingredientRepo.findByIngredientNameAndUserId(ingredientName, userId);
		
		if (ingredients.size() != 1) {
			return false;
		} 
		return true;
	}
}
