package com.chinmay.chaitanyabakingapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.chinmay.chaitanyabakingapp.R;
import com.chinmay.chaitanyabakingapp.Fragment.StepDetailsFragment;

import java.util.Objects;

public class StepDetailsActivity extends AppCompatActivity {
    private StepDetailsFragment detailsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        if(savedInstanceState == null)
        {
            Bundle bundle = getIntent().getExtras();

            if(Objects.requireNonNull(bundle).containsKey("name"))
            {
                Objects.requireNonNull(getSupportActionBar()).setTitle(bundle.getString("name")+" Steps");
            }
            bundle.putBoolean("tablet",false);

            detailsFragment = new StepDetailsFragment();
            detailsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentTwo, detailsFragment)
                    .commit();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState,"fragment",detailsFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        detailsFragment = (StepDetailsFragment) getSupportFragmentManager().getFragment(savedInstanceState,"fragment");
        if(Objects.requireNonNull(detailsFragment).isAdded())
        {
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentTwo, detailsFragment)
                .commit();
    }
}
