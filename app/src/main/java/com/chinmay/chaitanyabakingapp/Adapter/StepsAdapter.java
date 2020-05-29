package com.chinmay.chaitanyabakingapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chinmay.chaitanyabakingapp.R;
import com.chinmay.chaitanyabakingapp.Model.Step;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.RecyclerHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private final ArrayList<Step> steps;
    public int[] trackers;


    public StepsAdapter(Context context, ArrayList<Step> steps, int[] trackers) {
        this.context = context;
        this.steps = steps;
        inflater = LayoutInflater.from(context);
        this.trackers = trackers;
    }


    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.steps_list_item, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, final int position) {

        holder.title_txt.setText(steps.get(position).getShortDescription());
        if(trackers[position]==1)
        {
            holder.root.setBackgroundColor(ContextCompat.getColor(context,R.color.colorAccent));
            holder.title_txt.setTextColor(ContextCompat.getColor(context,R.color.white));
        }
        else{
            holder.root.setBackgroundColor(ContextCompat.getColor(context,R.color.gray));
            holder.title_txt.setTextColor(ContextCompat.getColor(context,R.color.black));
        }

    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    static class RecyclerHolder extends RecyclerView.ViewHolder {
        final TextView title_txt;
        final RelativeLayout root;

        RecyclerHolder(View itemView) {
            super(itemView);
            title_txt = itemView.findViewById(R.id.title);
            root = itemView.findViewById(R.id.root);
        }
    }

}
