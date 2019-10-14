package com.achen.recipeGenerator.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.achen.recipeGenerator.models.Recipe;

@Repository
@Transactional
public interface RecipeRepo extends MongoRepository<Recipe, String> {
	@Query("{ 'ingredients' : { $regex : ?0 } } ")
	List<Recipe> findByIngredientsIn(String name);

	Long deleteRecipeByTitle(String title);
	
	@Query("{ $or: [ { 'rating' : {$lt : 4} }, { 'rating' : null }, { 'sodium' : null } , { 'fat' : null }, { 'desc' : null }, { 'protein' : null } ] }")
	List<Recipe> deleteBadRecipes();
}
