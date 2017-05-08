package com.lorusso.luca.labeling;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.toOctalString;

/**
 * Created by Luca on 25/04/2017.
 */

class FreeAdapter extends
        RecyclerView.Adapter<FreeAdapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView textExercise;
        public TextView textDurata;
        public TextView textDescription;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            textExercise = (TextView) itemView.findViewById(R.id.description);
            textDurata = (TextView) itemView.findViewById(R.id.duration);
            textDescription = (TextView) itemView.findViewById(R.id.exerc);
        }


    }

    private List<Exercise> exercisesFree;
    // Store the context for easy access
    private Context mContext;

    public FreeAdapter(Context mContext, ArrayList<Exercise> exercises) {
        this.exercisesFree = exercises;
        this.mContext = mContext;
    }

    public void notifyData(ArrayList<Exercise> myList) {

        this.exercisesFree = myList;
        notifyDataSetChanged();
    }

    @Override
    public FreeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactViewExercise = inflater.inflate(R.layout.row_free_exercises, parent, false);

        // Return a new holder instance
        FreeAdapter.ViewHolder viewHolder = new FreeAdapter.ViewHolder(contactViewExercise);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FreeAdapter.ViewHolder holder, int position) {
        holder.textDescription.setText("Free exercise : ");
        holder.textExercise.setText(exercisesFree.get(position).getEsercizio().toString());
        holder.textDurata.setText(toOctalString(exercisesFree.get(position).getDurata()));
    }

    @Override
    public int getItemCount() {
        return exercisesFree.size();
    }

}
