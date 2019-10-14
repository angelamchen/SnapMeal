package com.achen.recipeGenerator.models.dto;

import java.util.List;

public class RecipeDto {
	List<String> directions; 
	int calories;
	String desc;
	String title;
	List<String> ingredients;
	double percentageMatch;
	
	public List<String> getDirections() {
		return directions;
	}
	
	public void setDirections(List<String> directions) {
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
	
	public List<String> getIngredients() {
		return ingredients;
	}
	
	public void setIngredients(List<String> ingredients) {
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
