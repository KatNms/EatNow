package com.recipebook.eatnow.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


/*
    Implement methods to query recipes from database
    Returned items are considered livedata recipe objects
 */
@Dao
public interface RecipeDAO {

    @Insert
    void insertRecipe(Recipe recipe);
    @Insert
    void insertAll(Recipe... recipes);

    @Query("DELETE from RECIPES")
    void deleteAllRecipes();

    @Query("SELECT * FROM RECIPES order by recipeTitle ASC")
    LiveData<List<Recipe>> getAllRecipes();


    @Query("SELECT * FROM RECIPES where id = :rId")
    Recipe getRecipeById(int rId);

    @Delete
    void deleteRecipe(Recipe recipe);

    @Update
    void updateRecipe(Recipe recipe);

    @Query("SELECT * FROM RECIPES where category LIKE :cValue")
    LiveData<List<Recipe>> getRecipeByCategory(String cValue);

}
