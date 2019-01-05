package com.recipebook.eatnow.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

/*
    create instance of repo and provides methods for Livedata recipe objects
    which are then used by the UI fragments to keep the database updated with
    any usser actions (add, delete, edit)
 */

public class RecipeViewModel extends AndroidViewModel {

    private RecipeRepository recipeRepository;
    private LiveData<List<Recipe>> recipeList;


    //get recipe list
    public RecipeViewModel(@NonNull Application application) {
        super(application);
        recipeRepository = new RecipeRepository(application);
        recipeList = recipeRepository.getRecipeList();
    }

    public LiveData<List<Recipe>> getRecipeList(){
        if (recipeList == null){
            recipeList = recipeRepository.getRecipeList();
        }
        return recipeList;
    }

    public LiveData<List<Recipe>> getRecipeByCategory(String category){
        return recipeRepository.getRecipeByCategory(category);
    }

    public void insertRecipe(Recipe recipe){
        recipeRepository.insertRecipe(recipe);
    }

    public void insertAll(Recipe... recipes){recipeRepository.insertAll(recipes);}

    public void updateRecipe(Recipe recipe){ recipeRepository.updateRecipe(recipe);}

    public void deleteRecipe(Recipe recipe) {recipeRepository.deleteRecipe(recipe);}

    public Recipe getRecipeById(int recipeId){
        return recipeRepository.getRecipeById(recipeId);
    }
}
