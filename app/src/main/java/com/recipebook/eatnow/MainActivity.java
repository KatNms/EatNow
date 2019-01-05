package com.recipebook.eatnow;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.recipebook.eatnow.db.Recipe;
import com.recipebook.eatnow.ui.HomeFragment;
import com.recipebook.eatnow.ui.RecipeDetailFragment;

public class MainActivity extends AppCompatActivity {

    //manage fragments to help fill container activity_main
    public static FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //blank container
        fragmentManager = getSupportFragmentManager();

        /*
            If there are fragments to fill container, return with the correct fragment
            Otherwise, start with home
         */
        if(findViewById(R.id.fragment_container) != null){
            if(savedInstanceState !=null){
                return;
            }
            fragmentManager.beginTransaction().add(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
    }

    /*
        Fragment is saved to back stack so the user can reverse the transaction and bring back the
        previous fragment
     */
    public void showDetail(Recipe recipe){
        RecipeDetailFragment recipeFragment = RecipeDetailFragment.forRecipe(recipe.getId());
        MainActivity.fragmentManager.beginTransaction().replace(R.id
                .fragment_container, recipeFragment).addToBackStack("recipe").commit();
    }

}
