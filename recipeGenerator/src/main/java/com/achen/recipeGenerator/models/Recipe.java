package com.achen.recipeGenerator.models;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "recipes")
public class Recipe {
	@Id
	ObjectId recipeId;
	ArrayList<String> directions; 
	String date;
	ArrayList<String> categories;
	String title;
	ArrayList<String> ingredients;
	
	@Override
	public String toString() {
		return "Recipe [recipeId=" + recipeId + ", directions=" + directions + ", date=" + date + ", categories="
				+ categories + ", title=" + title + ", ingredients=" + ingredients + "]";
	}
	public ObjectId getRecipeId() {
		return recipeId;
	}
	public void setRecipeId(ObjectId recipeId) {
		this.recipeId = recipeId;
	}
	public ArrayList<String> getDirections() {
		return directions;
	}
	public void setDirections(ArrayList<String> directions) {
		this.directions = directions;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public ArrayList<String> getCategories() {
		return categories;
	}
	public void setCategories(ArrayList<String> categories) {
		this.categories = categories;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<String> getIngredients() {
		return ingredients;
	}
	public void setIngredients(ArrayList<String> ingredients) {
		this.ingredients = ingredients;
	}

	
}
