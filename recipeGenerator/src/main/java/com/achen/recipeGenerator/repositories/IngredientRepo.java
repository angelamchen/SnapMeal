package com.achen.recipeGenerator.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.achen.recipeGenerator.models.Ingredient;

@Repository
public interface IngredientRepo extends MongoRepository<Ingredient, String> {
	
	@Query("{ingredientName: '?0'}")
	List<Ingredient> findAllByName(String name);
}
