package com.achen.recipeGenerator.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.achen.recipeGenerator.models.Dto.RecipeDto;
import com.achen.recipeGenerator.service.RecipeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
	private Gson gson = new GsonBuilder().create();
	
	@Autowired
	private RecipeService recipeService;
	
	@CrossOrigin
	@RequestMapping(value="/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAvailableRecipes(@PathVariable String userId) {
		try {
			List<RecipeDto> recipes = recipeService.getAvailableRecipes(userId);
			return new ResponseEntity<>(gson.toJson(recipes), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(String.format("Exception: %s, Class: %s , message: %s", e.toString(), e.getClass(), e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.DELETE)
	public String delete() {
		String message = recipeService.deleteStuff();
		return message;
	}
}
