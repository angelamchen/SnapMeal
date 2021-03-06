package com.achen.recipeGenerator.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.achen.recipeGenerator.models.dto.RecipeDto;

@Service("recipeService")
public interface RecipeService {
	public List<RecipeDto> getAvailableRecipes(String userId);

	public int cleanRecipes();
}
