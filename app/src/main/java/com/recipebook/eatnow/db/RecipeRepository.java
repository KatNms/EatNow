package com.recipebook.eatnow.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/*
    Responsible for interacting with room for ViewModel class
    Provides methods for DAO to query

    again, consists of query methods
 */
public class RecipeRepository {

    private RecipeDAO rRecipeDAO;
    private LiveData<List<Recipe>> recipeList;



    public RecipeRepository(Application application) {
        RecipeRoomDatabase db = RecipeRoomDatabase.getDatabase(application);
        rRecipeDAO = db.recipeDAO();
        recipeList = rRecipeDAO.getAllRecipes();
    }


    public void insertRecipe (Recipe recipe) {
        new insertAsyncTask(rRecipeDAO).execute(recipe);
    }
    public void insertAll(Recipe... recipes){rRecipeDAO.insertAll(recipes);}

    public void deleteRecipe (Recipe recipe){
        rRecipeDAO.deleteRecipe(recipe);
    }

    public void updateRecipe(Recipe recipe){rRecipeDAO.updateRecipe(recipe);}

    public LiveData<List<Recipe>> getRecipeList() {
        return recipeList;
        }


    public LiveData<List<Recipe>> getRecipeByCategory(String category){
        return rRecipeDAO.getRecipeByCategory(category);
    }

    public Recipe getRecipeById(int recipeId){
        return rRecipeDAO.getRecipeById(recipeId);
    }

    private static class insertAsyncTask extends AsyncTask<Recipe, Void, Void> {

        private RecipeDAO rAsyncTaskDao;

        insertAsyncTask(RecipeDAO dao) {
            rAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Recipe... params) {
            rAsyncTaskDao.insertRecipe(params[0]);
            return null;
        }
    }



}
