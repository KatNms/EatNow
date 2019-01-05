package com.recipebook.eatnow.ui;


import android.app.Fragment;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recipebook.eatnow.MainActivity;
import com.recipebook.eatnow.R;
import com.recipebook.eatnow.databinding.ItemRecyclerViewBinding;
import com.recipebook.eatnow.db.Recipe;
import com.recipebook.eatnow.db.RecipeViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeListFragment extends android.support.v4.app.Fragment {

    private RecipeAdapter rAdapter;
    private ItemRecyclerViewBinding binding;
    public RecipeListFragment(){
        //constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.item_recycler_view, container, false);
        rAdapter = new RecipeAdapter(rRecipeCallBack);
        binding.recyclerView.setAdapter(rAdapter);
        return binding.getRoot();

    }

    @Override
    //start viewmodel class
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel
                .class);
        subscribeUi(recipeViewModel);
    }

    private void subscribeUi(RecipeViewModel viewModel){
        viewModel.getRecipeByCategory(getArguments().getString("keyCategory")).observe(this, new
                Observer<List<Recipe>>
                () {
            @Override
            public void onChanged(@Nullable List<Recipe> recipeList) {
                if (recipeList != null) {
                    rAdapter.setRecipeList(recipeList);
                } else {

                    }
            }
        });

    }


    private final RecipeCallBack rRecipeCallBack = new RecipeCallBack() {
        @Override
        public void onClick(Recipe recipe) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)){
                ((MainActivity)getActivity()).showDetail(recipe);
            }
        }
    };

    public static RecipeListFragment sendCategory(String category){
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle args = new Bundle();
        args.putString("keyCategory", category);
        fragment.setArguments(args);
        return fragment;
    }

}
