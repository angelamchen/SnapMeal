package com.achen.recipeGenerator.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.achen.recipeGenerator.models.Recipe;

@Repository
@Transactional
public interface RecipeRepo extends MongoRepository<Recipe, String>{
	@Query("{title: '?0'}")
	List<Recipe> findAllByTitle(String title);
	
	@Query("{ ingredients: { $regex: '?0' } }")
	List<Recipe> findRecipesContainingIngredient(String name);
	

}
