package com.chinmay.chaitanyabakingapp.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chinmay.chaitanyabakingapp.Adapter.IngredientsAdapter;
import com.chinmay.chaitanyabakingapp.Adapter.StepsAdapter;
import com.chinmay.chaitanyabakingapp.Interface.FragmentOneListener;
import com.chinmay.chaitanyabakingapp.Model.Ingredient;
import com.chinmay.chaitanyabakingapp.Model.Step;
import com.chinmay.chaitanyabakingapp.R;
import com.chinmay.chaitanyabakingapp.RecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.Objects;

public class StepFragment extends Fragment {
    private FragmentOneListener listener;

    private RecyclerView recycler;
    private RecyclerView ingredientList;

    private ArrayList<Step> steps;
    private ArrayList<Ingredient> ingredients;

    private int[] trackers;
    private int index;

    private boolean tablet;

    private int x1;
    private int x2;

    public void setFragmentListener(FragmentOneListener listener) {
        this.listener = listener;
    }

    public StepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_step, container, false);
        recycler = root.findViewById(R.id.stepsList);
        ingredientList = root.findViewById(R.id.ingredientList);


        LinearLayoutManager ingredientsManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager stepsManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        if (savedInstanceState == null) {
            Bundle extra = getArguments();
            ingredients = Objects.requireNonNull(extra).getParcelableArrayList("ingredients");
            tablet = extra.getBoolean("tablet", false);
            steps = extra.getParcelableArrayList("steps");

            index = 0;
        } else {
            ingredients = savedInstanceState.getParcelableArrayList("ingredients");
            tablet = savedInstanceState.getBoolean("tablet", false);
            steps = savedInstanceState.getParcelableArrayList("steps");
            index = savedInstanceState.getInt("position");

            x1 = savedInstanceState.getInt("x1");
            x2 = savedInstanceState.getInt("x2");
        }
        trackers = new int[Objects.requireNonNull(steps).size()];
        if (tablet) {
            trackers[index] = 1;
        }


        ingredientList.setLayoutManager(ingredientsManager);
        ingredientList.setAdapter(new IngredientsAdapter(getActivity(), ingredients));
        if (x1 != 0) {
            Objects.requireNonNull(ingredientList.getLayoutManager()).scrollToPosition(x1);
        }


        recycler.setLayoutManager(stepsManager);
        recycler.setAdapter(new StepsAdapter(getActivity(), steps, trackers));
        if (x2 != 0) {
            Objects.requireNonNull(recycler.getLayoutManager()).scrollToPosition(x2);
        }

        recycler.addOnItemTouchListener(new RecyclerViewItemClickListener(getActivity(), recycler, new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        listener.setStep(position, steps);
                        updateView(position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
        if (tablet) {
            updateView(index);
            listener.setStep(index, steps);
        }

        return root;
    }


    public void updateView(int index) {
        this.index = index;
        if (!tablet) {
            return;
        }
        trackers = new int[steps.size()];
        try {
            trackers[index] = 1;
            ((StepsAdapter) Objects.requireNonNull(recycler.getAdapter())).trackers = trackers;
            recycler.getAdapter().notifyDataSetChanged();
            recycler.scrollToPosition(index);
        } catch (ArrayIndexOutOfBoundsException ignored) {

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("ingredients", ingredients);
        outState.putParcelableArrayList("steps", steps);
        outState.putBoolean("tablet", tablet);

        outState.putInt("position", index);

        outState.putInt("x1", ((LinearLayoutManager) Objects.requireNonNull(ingredientList.getLayoutManager())).findFirstCompletelyVisibleItemPosition());
        outState.putInt("x2", ((LinearLayoutManager) Objects.requireNonNull(recycler.getLayoutManager())).findFirstCompletelyVisibleItemPosition());


    }

}
