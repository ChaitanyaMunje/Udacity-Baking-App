package com.chinmay.chaitanyabakingapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chinmay.chaitanyabakingapp.Model.Ingredient;
import com.chinmay.chaitanyabakingapp.R;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.RecyclerHolder> {
    Context context;
    private LayoutInflater inflater;
    private ArrayList<Ingredient> ingredientArrayList;


    public IngredientsAdapter(Context context, ArrayList<Ingredient> ingredients) {
        this.context = context;
        this.ingredientArrayList = ingredients;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public IngredientsAdapter.RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.ingredient_list_item, null);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {

        holder.name_txt.setText(ingredientArrayList.get(position).getIngredient());
        holder.quantity_txt.setText("" + ingredientArrayList.get(position).getQuantity());
        holder.measure_txt.setText(ingredientArrayList.get(position).getMeasure());

    }


    @Override
    public int getItemCount() {
        return ingredientArrayList.size();
    }

    static class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView name_txt, quantity_txt, measure_txt;

        RecyclerHolder(View itemView) {
            super(itemView);
            name_txt = itemView.findViewById(R.id.name);
            quantity_txt = itemView.findViewById(R.id.quantity);
            measure_txt = itemView.findViewById(R.id.measure);
        }
    }
}
