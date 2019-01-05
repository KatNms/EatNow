package com.recipebook.eatnow.ui;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.view.View.OnClickListener;

import com.recipebook.eatnow.MainActivity;
import com.recipebook.eatnow.R;
import com.recipebook.eatnow.RegistrationActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    private ConstraintLayout addLayout;
    private FloatingActionButton addButton;
    private ConstraintLayout entreeLayout;
    private ConstraintLayout mainCourseLayout;
    private ConstraintLayout dessertLayout;
    private ConstraintLayout condimentLayout;
    private ConstraintLayout drinkLayout;

    public HomeFragment() {
        // constructor
    }

    //home page with different layouts

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        addLayout = view.findViewById(R.id.layout1);
        addButton = view.findViewById(R.id.floatingAddButton);
        entreeLayout = view.findViewById(R.id.layout2);
        mainCourseLayout = view.findViewById(R.id.layout3);
        dessertLayout = view.findViewById(R.id.layout4);
        condimentLayout = view.findViewById(R.id.layout5);
        drinkLayout = view.findViewById(R.id.layout6);
        Button editProfile = view.findViewById(R.id.editProfile);

        addLayout.setOnClickListener(this);
        addButton.setOnClickListener(this);
        entreeLayout.setOnClickListener(this);
        mainCourseLayout.setOnClickListener(this);
        dessertLayout.setOnClickListener(this);
        condimentLayout.setOnClickListener(this);
        drinkLayout.setOnClickListener(this);

        editProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), RegistrationActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    //layout determiend by which one the user clicks
    public void onClick(View v)
    {
        RecipeListFragment listFragment;
        switch (v.getId()){
            case R.id.floatingAddButton:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,
                        new AddRecipeFragment()).addToBackStack(null).commit();
                break;
            case R.id.layout1:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,
                        new AddRecipeFragment()).addToBackStack(null).commit();
                break;
            case R.id.layout2:
                listFragment = RecipeListFragment.sendCategory("Entree");
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,
                       listFragment).addToBackStack(null).commit();
                break;
            case R.id.layout3:
                listFragment = RecipeListFragment.sendCategory("Main Course");
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,
                        listFragment).addToBackStack(null).commit();
                break;
            case R.id.layout4:
                listFragment = RecipeListFragment.sendCategory("Dessert");
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,
                        listFragment).addToBackStack(null).commit();
                break;
            case R.id.layout5:
                listFragment = RecipeListFragment.sendCategory("Anything Else!");
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,
                        listFragment).addToBackStack(null).commit();
                break;
            case R.id.layout6:
                listFragment = RecipeListFragment.sendCategory("Drinks");
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,
                        listFragment).addToBackStack(null).commit();
                break;
        }

    }



}
