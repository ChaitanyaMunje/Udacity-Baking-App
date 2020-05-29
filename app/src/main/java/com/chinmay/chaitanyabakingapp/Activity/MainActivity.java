package com.chinmay.chaitanyabakingapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.idling.CountingIdlingResource;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chinmay.chaitanyabakingapp.Adapter.RecipesAdapter;
import com.chinmay.chaitanyabakingapp.Interface.OnRequestFinishedListener;
import com.chinmay.chaitanyabakingapp.Model.Recipe;
import com.chinmay.chaitanyabakingapp.NetworkOperations;
import com.chinmay.chaitanyabakingapp.R;
import com.chinmay.chaitanyabakingapp.RecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Response;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements OnRequestFinishedListener {

    private ArrayList<Recipe> recipes;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Button reload_btn;
    private int position;

    private final CountingIdlingResource mIdlingResource = new CountingIdlingResource("Loading_Data");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progressBar);
        reload_btn = findViewById(R.id.reload);

        if (savedInstanceState == null) {
            mIdlingResource.increment();
            NetworkOperations.getRecipes(this);
        }


        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(MainActivity.this, recyclerView, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent details = new Intent(MainActivity.this, RecipeDetailsActivity.class);

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("steps",
                        (ArrayList<? extends Parcelable>) recipes.get(position).getSteps());
                bundle.putParcelableArrayList("ingredients",
                        (ArrayList<? extends Parcelable>) recipes.get(position).getIngredients());
                bundle.putString("recipe_name", recipes.get(position).getName());
                details.putExtra("bundle", bundle);

                startActivity(details);

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }


    @Override
    public void onFailure() {
        progressBar.setVisibility(GONE);
        reload_btn.setVisibility(View.VISIBLE);
        Toast.makeText(MainActivity.this, "No internet connection !", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResponse(Response<List<Recipe>> response) {

        recipes = (ArrayList<Recipe>) response.body();

        reload_btn.setVisibility(GONE);
        progressBar.setVisibility(GONE);

        renderRecyclerView();

        mIdlingResource.decrement();
    }

    private void renderRecyclerView() {
        if (recyclerView.getTag().equals("tablet")) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 3);
            recyclerView.setLayoutManager(gridLayoutManager);
        } else {
            LinearLayoutManager gridLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
        }
        recyclerView.setAdapter(new RecipesAdapter(MainActivity.this, recipes));
        Objects.requireNonNull(recyclerView.getLayoutManager()).scrollToPosition(position);
    }


    public void reload() {
        progressBar.setVisibility(View.VISIBLE);
        reload_btn.setVisibility(GONE);
        NetworkOperations.getRecipes(this);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("recipes", recipes);
        if (recyclerView.getTag().equals("tablet")) {
            outState.putInt("position", ((GridLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager()))
                    .findFirstCompletelyVisibleItemPosition());
        } else {
            outState.putInt("position", ((LinearLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager()))
                    .findFirstCompletelyVisibleItemPosition());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recipes = savedInstanceState.getParcelableArrayList("recipes");
        position = savedInstanceState.getInt("position");
        renderRecyclerView();
        progressBar.setVisibility(GONE);
    }


    @VisibleForTesting
    @NonNull
    public CountingIdlingResource getIdlingResource() {
        return mIdlingResource;
    }

}
