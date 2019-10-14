package com.achen.recipeGenerator.models;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "recipes")
public class Recipe {
	@Id
	ObjectId recipeId;
	List<String> directions; 
	String date;
	List<String> categories;
	String title;
	List<String> ingredients;
	
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
	public List<String> getDirections() {
		return directions;
	}
	public void setDirections(List<String> directions) {
		this.directions = directions;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}

	
}
