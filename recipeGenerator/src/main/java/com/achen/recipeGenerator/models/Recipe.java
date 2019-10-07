package com.achen.recipeGenerator.models;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
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
	
	@Override
	public String toString() {
		return "Recipe [recipeId=" + recipeId + ", directions=" + directions + ", fat=" + fat + ", date=" + date
				+ ", categories=" + categories + ", calories=" + calories + ", desc=" + desc + ", protein=" + protein
				+ ", rating=" + rating + ", title=" + title + ", ingredients=" + ingredients + ", sodium=" + sodium
				+ "]";
	}
}
