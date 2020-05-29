package com.chinmay.chaitanyabakingapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.chinmay.chaitanyabakingapp.Interface.FragmentOneListener;
import com.chinmay.chaitanyabakingapp.Model.Step;
import com.chinmay.chaitanyabakingapp.R;
import com.chinmay.chaitanyabakingapp.Fragment.StepDetailsFragment;
import com.chinmay.chaitanyabakingapp.Fragment.StepFragment;

import java.util.ArrayList;
import java.util.Objects;

public class RecipeDetailsActivity extends AppCompatActivity implements FragmentOneListener {
    private FrameLayout detailFragment;
    private boolean Tablet;
    private String name;
    private StepDetailsFragment detailsFragment;
    private StepFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        detailFragment = findViewById(R.id.fragmentTwo);
        Tablet = true;
        Bundle extras = getIntent().getBundleExtra("bundle");
        name = Objects.requireNonNull(extras).getString("recipe_name");
        Objects.requireNonNull(getSupportActionBar()).setTitle(name);
        ArrayList<Step> steps = extras.getParcelableArrayList("steps");
        extras.putBoolean("tablet", (detailFragment != null));

        if (savedInstanceState == null) {
            fragment = new StepFragment();
            fragment.setFragmentListener(this);
            fragment.setArguments(extras);
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentOne, fragment).commit();
            if (detailFragment == null) {
                Tablet = false;
            } else {
                this.setStep(0, steps);
            }
        } else {
            fragment = (StepFragment) getSupportFragmentManager().getFragment(savedInstanceState, "main");
            Objects.requireNonNull(fragment).setFragmentListener(this);


            if (!fragment.isAdded())
                getSupportFragmentManager().beginTransaction().add(R.id.fragmentOne, fragment).commit();

            if (detailsFragment != null) {
                detailsFragment = (StepDetailsFragment) getSupportFragmentManager().getFragment(savedInstanceState, "detail");

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentTwo, detailsFragment).commit();

            }
        }


    }

    @Override
    public void setStep(int index, ArrayList<Step> steps) {
        if (!Tablet) {
            Intent intent = new Intent(this, StepDetailsActivity.class);
            intent.putExtra("steps", steps);
            intent.putExtra("current", index);
            intent.putExtra("name", name);
            startActivity(intent);
        } else {
            detailsFragment = new StepDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("steps", steps);
            detailsFragment.setFragmentListener(this);
            bundle.putInt("current", index);
            bundle.putBoolean("tablet", true);
            detailsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentTwo, detailsFragment).commit();
        }
    }

    @Override
    public void setCurrent(int index) {
        if (Tablet) {
            fragment.updateView(index);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "main", fragment);
        if (Tablet && detailFragment != null) {
            try {
                getSupportFragmentManager().putFragment(outState, "detail", detailsFragment);
            } catch (NullPointerException ignored) {
            }
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (detailFragment == null) {
            Tablet = false;
        }
    }
}
