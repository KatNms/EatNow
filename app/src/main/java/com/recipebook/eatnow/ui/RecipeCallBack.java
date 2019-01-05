package com.recipebook.eatnow.ui;

import com.recipebook.eatnow.db.Recipe;

//interface so mainactivity knows when actions in fragments are called
public interface RecipeCallBack {
    void onClick(Recipe recipe);
}
