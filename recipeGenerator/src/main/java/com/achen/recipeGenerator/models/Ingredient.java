package com.achen.recipeGenerator.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "ingredients")
public class Ingredient {
	
	String userId;
	
	@Id
	String ingredientName;
	
	Date date;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Ingredient [userId=" + userId + ", ingredientName=" + ingredientName + ", date=" + date
				+ "]";
	}
}
