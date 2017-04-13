package com.lorusso.luca.labeling;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.renderscript.Sampler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Exercise exercise = exercises.get(position);
        if (position == 0) {
            TextView textExercise = ViewHolder.textExercise;
            textExercise.setText(exercise.getEsercizio());

            TextView textDurata = ViewHolder.textDurata;
            textDurata.setText("Durata : " + exercise.getDurata());

            final Button butt1 = ViewHolder.buttonStartConst;

            butt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance();
                    long nowStart = calendar.getTimeInMillis();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                    String today = dateFormat.format(calendar.getTime());
                    dateFormat.applyPattern("HH:mm.ss");
                    String time = dateFormat.format(calendar.getTime());



                    try {
                        File dataLabeling = new File(Environment.getExternalStorageDirectory()
                                + "/DataLabeling");
                        if (!dataLabeling.exists())
                            dataLabeling.mkdir();

                        File userDir = new File(dataLabeling.toString(), "/" + "ciao");
                        if (!userDir.exists()) {
                            userDir.mkdir();
                        }
                        File userDirMode = new File(userDir.toString(), "/Costraint");
                        if (!userDirMode.exists()) {
                            userDirMode.mkdir();
                        }



                        File userDirExercise = new File(userDirMode.toString(), "/" + "ciaociao");
                        if (!userDirExercise.exists()) {
                            userDirExercise.mkdir();
                        }

                        File userDirExerciseDay = new File(userDirExercise.toString(), "/" + today);

                        if (!userDirExerciseDay.exists()) {
                            userDirExerciseDay.mkdir();
                        }

                        File userDirExerciseDayHour = new File(userDirExerciseDay.toString(), "/" + time);

                        if (!userDirExerciseDayHour.exists()) {
                            userDirExerciseDayHour.mkdir();
                        }


                        File outputFileFree = new File(userDirExerciseDayHour.toString(), "mydata.csv");

                        outputFileFree.createNewFile();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        } else {
            TextView textExercise = ViewHolder.textExercise;
            textExercise.setText(exercise.getEsercizio());
            textExercise.setTextColor(ContextCompat.getColor(mContext,R.color.colorToHide));

            TextView textDurata = ViewHolder.textDurata;
            textDurata.setText("Durata : " + exercise.getDurata());
            textDurata.setTextColor(ContextCompat.getColor(mContext,R.color.colorToHide));

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
