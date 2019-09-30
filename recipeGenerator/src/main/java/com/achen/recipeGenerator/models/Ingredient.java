package com.achen.recipeGenerator.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "ingredients")
public class Ingredient {
	@Transient
	public static final String SEQUENCE_NAME = "ingredient_sequence";
	
	@Id
	long id;
	
	int userId;
	
	String ingredientName;
	
	Date date;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
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

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", userId=" + userId + ", ingredientName=" + ingredientName + ", date=" + date
				+ "]";
	}
}
