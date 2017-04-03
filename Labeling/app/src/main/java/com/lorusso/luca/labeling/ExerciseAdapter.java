package com.lorusso.luca.labeling;

import android.content.Context;
import android.renderscript.Sampler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luca on 30/03/2017.
 */

public class ExerciseAdapter extends
        RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public static TextView textExercise;
        public static TextView textDurata;
        public static Button buttonStartConst;
        public static Button buttonRestart;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);


            textExercise = (TextView) itemView.findViewById(R.id.textExercise);
            textDurata = (TextView) itemView.findViewById(R.id.textDurata);

            buttonStartConst = (Button) itemView.findViewById(R.id.buttonStartConst);
            buttonRestart = (Button) itemView.findViewById(R.id.buttonRestart);
            itemView.findViewById(R.id.buttonStartConst).setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Toast.makeText(itemView.getContext(), "The Item Clicked is: " + getPosition(), Toast.LENGTH_SHORT).show();

        }


    }


    private List<Exercise> exercises;
    // Store the context for easy access
    private Context mContext;

    public ExerciseAdapter(Context mContext, ArrayList<Exercise> exercises) {
        this.exercises = exercises;
        this.mContext = mContext;
    }

    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.rows_exercise, parent, false);

        // Return a new holder instance
        ExerciseAdapter.ViewHolder viewHolder = new ExerciseAdapter.ViewHolder(contactView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        if (position == 0) {
            TextView textExercise = ViewHolder.textExercise;
            textExercise.setText(exercise.getEsercizio());

            TextView textDurata = ViewHolder.textDurata;
            textDurata.setText("Durata : " + exercise.getDurata());
        } else {
            TextView textExercise = ViewHolder.textExercise;
            textExercise.setText(exercise.getEsercizio());
            textExercise.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorToHide));

            TextView textDurata = ViewHolder.textDurata;
            textDurata.setText("Durata : " + exercise.getDurata());
            textDurata.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorToHide));

            Button butt1 = ViewHolder.buttonStartConst;
            butt1.setClickable(false);
            butt1.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorToHide));

            Button butt2 = ViewHolder.buttonRestart;
            butt2.setClickable(false);
            butt2.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorToHide));

        }


    }


    @Override
    public int getItemCount() {
        return exercises.size();
    }
}
