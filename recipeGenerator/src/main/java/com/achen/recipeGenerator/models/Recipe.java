package com.achen.recipeGenerator.models;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "recipes")
public class Recipe {
	ObjectId recipeId;
	ArrayList<String> directions; 
	int fat;
	String date;
	ArrayList<String> categories;
	int calories;
	String desc;
	int protein;
	float rating;
	String title;
	ArrayList<String> ingredients;
	int sodium;
	
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

	public int getFat() {
		return fat;
	}

	public void setFat(int fat) {
		this.fat = fat;
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

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getProtein() {
		return protein;
	}

	public void setProtein(int protein) {
		this.protein = protein;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
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

	public int getSodium() {
		return sodium;
	}

	public void setSodium(int sodium) {
		this.sodium = sodium;
	}

	@Override
	public String toString() {
		return "Recipe [recipeId=" + recipeId + ", directions=" + directions + ", fat=" + fat + ", date=" + date
				+ ", categories=" + categories + ", calories=" + calories + ", desc=" + desc + ", protein=" + protein
				+ ", rating=" + rating + ", title=" + title + ", ingredients=" + ingredients + ", sodium=" + sodium
				+ "]";
	}
}
