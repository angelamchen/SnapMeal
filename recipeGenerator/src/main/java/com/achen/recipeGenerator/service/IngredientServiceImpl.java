package com.achen.recipeGenerator.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.achen.recipeGenerator.models.ImageRequestDto;
import com.achen.recipeGenerator.models.Ingredient;
import com.achen.recipeGenerator.repositories.IngredientRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
	private Gson gson = new GsonBuilder().create();
	
	final ClarifaiClient client = new ClarifaiBuilder("1100f3670e8f45c78bfaffec39c36deb")
			.client(new OkHttpClient()) // OPTIONAL. Allows customization of OkHttp by the user
			.buildSync(); // or use .build() to get a Future<ClarifaiClient>

	@Autowired
	IngredientRepo ingredientRepo;

	// This should not be a response Entity...
	@Override
	public ResponseEntity<?> addIngredientFromText(String ingredientName, String userId) {
		try {
			
			List<Ingredient> ingredient = findUserIngredientsByName(ingredientName, userId);
			
			if (!ingredient.isEmpty()) {
				return new ResponseEntity<>("Ingredient already exists", HttpStatus.OK);
			}
			
			Date dateobj = new Date();

			Ingredient newIngredient = new Ingredient();
			newIngredient.setIngredientName(ingredientName);
			newIngredient.setUserId(userId);
			newIngredient.setDate(dateobj);

			ingredientRepo.save(newIngredient);

			return new ResponseEntity<>(String.format("Ingredient %s saved", newIngredient), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
		}
	}
	
	//TODO: implement add ingredient
	
	@Override
	public List<Ingredient> findUserIngredientsByName(String ingredientName, String userId) {
		return ingredientRepo.findByIngredientNameAndUserId(ingredientName, userId);
	}

	@Override
	public ResponseEntity<?> getIngredientByName(String name) {
		List<Ingredient> ingredients = ingredientRepo.findAllByName(name);

		if (ingredients.size() != 1) {
			// error that none or too much are found
			return new ResponseEntity<>(String.format("Could not find: %s", name), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(gson.toJson(ingredients.get(0)), HttpStatus.OK);
	}

	@Override
	public List<Ingredient> getAllIngredientsByUser(String userId) {
			List<Ingredient> ingredients = ingredientRepo.findAllByUserId(userId);
			return ingredients;
	}
	
	@Override
	public ResponseEntity<?> addIngredientFromImage(ImageRequestDto imageProp, String userId) {
		byte[] imageBytes = Base64.getDecoder().decode(imageProp.getImageString());
		
		Model<Concept> foodModel = client.getDefaultModels().foodModel();

		PredictRequest<Concept> request = foodModel.predict().withInputs(
		        ClarifaiInput.forImage(imageBytes));
		List<ClarifaiOutput<Concept>> results = request.executeSync().get();
		
		List<Concept> retrievedIngredients = results.get(0).data();
		List<Ingredient> validIngredients = new ArrayList<>();
		
		for (Concept ingredient : retrievedIngredients) {
			if (ingredient.value() > 0.95) {
				Date dateobj = new Date();

				Ingredient newIngredient = new Ingredient();
				newIngredient.setIngredientName(ingredient.name());
				newIngredient.setUserId(userId);
				newIngredient.setDate(dateobj);

				// TODO: there should be no ingredient that is the same for each user. Right now if ingredient exists, it is overrided
				ingredientRepo.save(newIngredient);
				validIngredients.add(newIngredient);
			}
		}
		
		return new ResponseEntity<>(gson.toJson(validIngredients), HttpStatus.OK);
	}
}
