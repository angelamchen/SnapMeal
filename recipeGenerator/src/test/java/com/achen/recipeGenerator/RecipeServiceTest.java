package com.achen.recipeGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.achen.recipeGenerator.models.Ingredient;
import com.achen.recipeGenerator.models.Recipe;
import com.achen.recipeGenerator.models.dto.RecipeDto;
import com.achen.recipeGenerator.repositories.RecipeRepo;
import com.achen.recipeGenerator.service.IngredientService;
import com.achen.recipeGenerator.service.RecipeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceTest {
	@Mock
	IngredientService mockIngredientService;
	
	@Mock
	RecipeRepo mockRecipeRepo;
	
	@InjectMocks
	RecipeServiceImpl mockRecipeServiceImpl;
	
	@Test
	public void findMatchPercentage_baseTest() {
		// Arrange
		List<String> recipeIngredients = new ArrayList<>();
		recipeIngredients.add("4 6- to 7-ounce beef tenderloin steaks (each about 1 inch thick)");
		recipeIngredients.add("1 tablespoon olive oil");
		recipeIngredients.add("3 tablespoons chopped fresh parsley");
		recipeIngredients.add("3 large garlic cloves, chopped");
		recipeIngredients.add("2/3 cup canned beef broth");
		recipeIngredients.add("2 tablespoons brandy");
		
		List<Ingredient> ingredients = new ArrayList<>();
		Ingredient beef = new Ingredient();
		beef.setIngredientName("beef");
		Ingredient parsley = new Ingredient();
		parsley.setIngredientName("parsley");
		Ingredient garlic = new Ingredient();
		garlic.setIngredientName("garlic");
		Ingredient brandy = new Ingredient();
		brandy.setIngredientName("brandy");
		
		ingredients.add(beef);
		ingredients.add(parsley);
		ingredients.add(garlic);
		ingredients.add(brandy);
		
		// Act
		double match = mockRecipeServiceImpl.findMatchPercentage(recipeIngredients, ingredients);
		
		// Assert
		assertTrue(match < 0.84);
		assertTrue(match > 0.83);
		
	}
	
	@Test
	public void getAvailableRecipes_baseTest() {
		String mockUserId = "userId";
		String mockRecipeTitle = "Beef";
		
		// Mocks for retrieving user ingredients
		Ingredient beef = new Ingredient();
		beef.setIngredientName("beef");
		
		Ingredient parsley = new Ingredient();
		parsley.setIngredientName("parsley");
		
		Ingredient garlic = new Ingredient();
		garlic.setIngredientName("garlic");
		
		Ingredient brandy = new Ingredient();
		brandy.setIngredientName("brandy");
		
		List<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(beef);
		ingredients.add(parsley);
		ingredients.add(garlic);
		ingredients.add(brandy);
		
		doReturn(ingredients).when(mockIngredientService).getAllIngredientsByUser(mockUserId);
		
		// Mocks for retrieving recipes
		
		List<String> beefRecipeIngredients = new ArrayList<>();
		beefRecipeIngredients.add("4 6- to 7-ounce beef tenderloin steaks (each about 1 inch thick)");
		beefRecipeIngredients.add("1 tablespoon olive oil");
		beefRecipeIngredients.add("3 tablespoons chopped fresh parsley");
		beefRecipeIngredients.add("3 large garlic cloves, chopped");
		beefRecipeIngredients.add("2/3 cup canned beef broth");
		beefRecipeIngredients.add("2 tablespoons brandy");
		
		Recipe beefRecipe = new Recipe();
		beefRecipe.setIngredients(beefRecipeIngredients);
		beefRecipe.setTitle(mockRecipeTitle);
		
		List<Recipe> recipes = new ArrayList<>();
		recipes.add(beefRecipe);
		
		doReturn(recipes).when(mockRecipeRepo).findByIngredientsIn(any());
		
		// Act
		List<RecipeDto> matchedRecipes = mockRecipeServiceImpl.getAvailableRecipes(mockUserId);
		
		// Assert
		// No duplicates should exists, recipe should also be matched
		assertEquals(1, matchedRecipes.size());
		assertEquals(mockRecipeTitle, matchedRecipes.get(0).getTitle());
	}
	
	@Test
	public void getAvailableRecipes_noMatch() {
		String mockUserId = "userId";
		String mockRecipeTitle = "Beef";
		
		// Mocks for retrieving user ingredients
		Ingredient tomato = new Ingredient();
		tomato.setIngredientName("tomato");
		
		List<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(tomato);
		
		doReturn(ingredients).when(mockIngredientService).getAllIngredientsByUser(mockUserId);
		
		// Mocks for retrieving recipes
		
		List<String> beefRecipeIngredients = new ArrayList<>();
		beefRecipeIngredients.add("4 6- to 7-ounce beef tenderloin steaks (each about 1 inch thick)");
		beefRecipeIngredients.add("1 tablespoon olive oil");
		beefRecipeIngredients.add("3 large garlic cloves, chopped");
		beefRecipeIngredients.add("2/3 cup canned beef broth");
		
		Recipe beefRecipe = new Recipe();
		beefRecipe.setIngredients(beefRecipeIngredients);
		beefRecipe.setTitle(mockRecipeTitle);
		
		List<Recipe> recipes = new ArrayList<>();
		recipes.add(beefRecipe);
		
		doReturn(recipes).when(mockRecipeRepo).findByIngredientsIn(any());
		
		// Act
		List<RecipeDto> matchedRecipes = mockRecipeServiceImpl.getAvailableRecipes(mockUserId);
		
		// Assert
		assertEquals(0, matchedRecipes.size());
	}
	
	@Test
	public void getAvailableRecipes_percentageMatchFail() {
		String mockUserId = "userId";
		String mockRecipeTitle = "Beef";
		
		// Mocks for retrieving user ingredients
		Ingredient tomato = new Ingredient();
		tomato.setIngredientName("tomato");
		
		Ingredient parsley = new Ingredient();
		parsley.setIngredientName("parsley");
		
		Ingredient garlic = new Ingredient();
		garlic.setIngredientName("garlic");
		
		Ingredient apple = new Ingredient();
		apple.setIngredientName("apple");
		
		List<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(tomato);
		ingredients.add(parsley);
		ingredients.add(garlic);
		ingredients.add(apple);
		
		doReturn(ingredients).when(mockIngredientService).getAllIngredientsByUser(mockUserId);
		
		// Mocks for retrieving recipes
		
		List<String> beefRecipeIngredients = new ArrayList<>();
		beefRecipeIngredients.add("4 6- to 7-ounce beef tenderloin steaks (each about 1 inch thick)");
		beefRecipeIngredients.add("1 tablespoon olive oil");
		beefRecipeIngredients.add("3 tablespoons chopped fresh parsley");
		beefRecipeIngredients.add("3 large garlic cloves, chopped");
		beefRecipeIngredients.add("2/3 cup canned beef broth");
		beefRecipeIngredients.add("2 tablespoons brandy");
		
		Recipe beefRecipe = new Recipe();
		beefRecipe.setIngredients(beefRecipeIngredients);
		beefRecipe.setTitle(mockRecipeTitle);
		
		List<Recipe> recipes = new ArrayList<>();
		recipes.add(beefRecipe);
		
		doReturn(recipes).when(mockRecipeRepo).findByIngredientsIn(any());
		
		// Act
		List<RecipeDto> matchedRecipes = mockRecipeServiceImpl.getAvailableRecipes(mockUserId);
		
		// Assert
		assertEquals(0, matchedRecipes.size());
	}
	
}
