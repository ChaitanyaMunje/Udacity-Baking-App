package com.chinmay.chaitanyabakingapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chinmay.chaitanyabakingapp.R;
import com.chinmay.chaitanyabakingapp.Model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecyclerHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private final ArrayList<Recipe> recipeArrayList;


    public RecipesAdapter(Context context, ArrayList<Recipe> recipes) {
        this.context = context;
        this.recipeArrayList = recipes;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recipes_list_item, null);
        return new RecyclerHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerHolder holder, final int position) {

        holder.recipeName_txt.setText(recipeArrayList.get(position).getName());
        holder.recipeStepsCount_txt.setText("" + recipeArrayList.get(position).getSteps().size());

        Glide.with(context)
                .load(recipeArrayList.get(position).getImage())
                .placeholder(R.drawable.ic_cupcake)
                .into(holder.recipeImage);


    }


    @Override
    public int getItemCount() {
        return recipeArrayList.size();
    }

    static class RecyclerHolder extends RecyclerView.ViewHolder {
        final TextView recipeName_txt;
        final TextView recipeStepsCount_txt;
        final ImageView recipeImage;

        RecyclerHolder(View itemView) {
            super(itemView);
            recipeName_txt = itemView.findViewById(R.id.recipe_name);
            recipeStepsCount_txt = itemView.findViewById(R.id.recipe_steps_count);
            recipeImage = itemView.findViewById(R.id.image);

        }
    }
}
