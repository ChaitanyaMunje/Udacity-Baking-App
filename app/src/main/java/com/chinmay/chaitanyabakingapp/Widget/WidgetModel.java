package com.chinmay.chaitanyabakingapp.Widget;

import com.chinmay.chaitanyabakingapp.Model.Ingredient;

import java.util.ArrayList;

public class WidgetModel {

    public final String recipeTitle;
    public final ArrayList<Ingredient> ingredients;

    public WidgetModel(String recipeTitle, ArrayList<Ingredient> ingredients) {
        this.recipeTitle = recipeTitle;
        this.ingredients = ingredients;
    }

}
