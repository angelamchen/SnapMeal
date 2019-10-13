package com.achen.recipeGenerator.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.achen.recipeGenerator.models.Ingredient;

@Repository
@Transactional
public interface IngredientRepo extends MongoRepository<Ingredient, String> {
	
	@Query("{ ingredientName : ?0 }")
	List<Ingredient> findAllByName(String name);

	@Query("{ userId : ?0 }")
	List<Ingredient> findAllByUserId(String userId);

	@Query("{ ingredientName : ?0 }, { userId : ?1 }")
	List<Ingredient> findByIngredientNameAndUserId(String ingredientName, String userId);
}
