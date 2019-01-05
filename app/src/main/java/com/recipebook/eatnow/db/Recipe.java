package com.recipebook.eatnow.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


//room database table
@Entity(tableName = "RECIPES")
public class Recipe{

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String recipeTitle;
    private String category;
    private String serving;
    private String prepTime;
    private String cookingTime;
    private String ingredients;
    private String instructions;
    private String imagePath;

    public Recipe() {
    }

    //items for each recipe object
    @Ignore
    public Recipe(@NonNull String recipeTitle, String category, String serving, String prepTime, String cookingTime, String ingredients, String instructions, String imagePath) {
        this.recipeTitle = recipeTitle;
        this.category = category;
        this.serving = serving;
        this.prepTime = prepTime;
        this.cookingTime = cookingTime;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.imagePath = imagePath;
    }


    //methods for database queries
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getRecipeTitle() {
        return recipeTitle;
    }

    public void setRecipeTitle(@NonNull String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getServing() {
        return serving;
    }

    public void setServing(String serving) {
        this.serving = serving;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    //setting default recipes
    public static Recipe[] dataSample(){
        return new Recipe[]{
                new Recipe("Fancy Potato", "Entree", "2 people", "20 mins", "45 mins", "2 potatos, " + "olive oil", "Cook the potato with a cast iron", "potato.jpeg"),

                new Recipe("Sunrise Drink", "Drinks","5 drinks","1 min", "2","alcohol, " + "orange juice","Shake well","drink.jpeg"),

                new Recipe("Red bean ice cream", "Dessert","2 Quarts", "30 mins", "overnight", "3 cups whole milk, " + "1 cup heavy cream, " +  "2/3 cups sugar, "+ "7/4 cups red bean paste", "Mix ingredients in a bowl and set in fridge overnight", "redbean.jpeg")
        };
    }
}
