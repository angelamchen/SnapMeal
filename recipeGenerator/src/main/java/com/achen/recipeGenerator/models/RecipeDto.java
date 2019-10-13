package com.achen.recipeGenerator.models;

import java.util.ArrayList;

public class RecipeDto {
	ArrayList<String> directions; 
	int calories;
	String desc;
	String title;
	ArrayList<String> ingredients;
	double percentageMatch;
	
	public ArrayList<String> getDirections() {
		return directions;
	}
	
	public void setDirections(ArrayList<String> directions) {
		this.directions = directions;
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
	
	public double getPercentageMatch() {
		return percentageMatch;
	}
	
	public void setPercentageMatch(double percentageMatch) {
		this.percentageMatch = percentageMatch;
	}

	@Override
	public String toString() {
		return "RecipeDto [directions=" + directions + ", calories=" + calories + ", desc=" + desc + ", title=" + title
				+ ", ingredients=" + ingredients + ", percentageMatch=" + percentageMatch + "]";
	}
}
