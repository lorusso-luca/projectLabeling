package com.lorusso.luca.labeling;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Environment;
import android.renderscript.Sampler;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static com.lorusso.luca.labeling.R.id.buttonStartConst;
import static java.lang.Long.toOctalString;

/**
 * Created by Luca on 30/03/2017.
 */

public class ExerciseAdapter extends
        RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Your holder should contain a member variable
        // for any view that will be set as you render a row

        public TextView textExercise;
        public TextView textDurata;
        public Button buttonStartConst;
        public Button buttonRestart;


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
            //itemView.findViewById(R.id.buttonStartConst).setOnClickListener(this);


        }

    }


    private List<Exercise> exercises;
    // Store the context for easy access
    private Context mContext;
    String timeStart;
    int pos = 0;

    public ExerciseAdapter(Context mContext, ArrayList<Exercise> exercises) {
        this.exercises = exercises;
        this.mContext = mContext;
    }

    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactViewExercise = inflater.inflate(R.layout.rows_exercise, parent, false);

        // Return a new holder instance
        ExerciseAdapter.ViewHolder viewHolder = new ExerciseAdapter.ViewHolder(contactViewExercise);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Exercise exercise = exercises.get(position);

        holder.textExercise.setText(exercise.getEsercizio());;

        holder.textDurata.setText("Durata: " + exercise.getDurata());


        holder.buttonStartConst.setTag(position);
        pos++;

        //ViewHolder.buttonRestart.setTag(position);


//nooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo

        if (position != 0) {
            holder.textExercise.setTextColor(ContextCompat.getColor(mContext, R.color.colorToHide));
            holder.textDurata.setTextColor(ContextCompat.getColor(mContext, R.color.colorToHide));
            holder.buttonStartConst.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorToHide));
            holder.buttonStartConst.setClickable(false);
            holder.buttonRestart.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorToHide));
            holder.buttonRestart.setClickable(false);
        }
        holder.buttonStartConst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAction((Integer) v.getTag(),holder);

                holder.buttonStartConst.setText(((Integer) v.getTag()).toString());
            }
        });

    }

    public StringBuilder total = new StringBuilder();

    public void doAction(int i,ViewHolder holder) {

        if (i == 0) {
            total.append("Start");
            total.append(",");
            total.append("Stop");
            total.append("\n");
            createTuple(i,holder);
            Toast.makeText(mContext, toOctalString(i), Toast.LENGTH_LONG).show();
        } else if (i >= 1 && i < exercises.size() - 1) {
            createTuple(i,holder);
            total.append("\n");
        } else {
            total.append("\n");
            createTuple(i,holder);
            Toast.makeText(mContext, toOctalString(i) + "ultimo", Toast.LENGTH_LONG).show();

        }


    }


    public boolean createTuple(final int i, final ViewHolder holder) {
        File outputFileConstr = null;
        Toast.makeText(mContext, toOctalString(i), Toast.LENGTH_LONG).show();
        Calendar calendar = Calendar.getInstance();
        long nowStart = calendar.getTimeInMillis();

        String user = ((Activity) mContext).getIntent().getStringExtra("user");
        final String descProtocol = ((Activity) mContext).getIntent().getStringExtra("descProtocol");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        final String today = dateFormat.format(calendar.getTime());
        dateFormat.applyPattern("HH:mm");
        String time = dateFormat.format(calendar.getTime());

        try {

            File dataLabeling = new File(Environment.getExternalStorageDirectory()
                    + "/DataLabeling");
            if (!dataLabeling.exists())
                dataLabeling.mkdir();


            File userDir = new File(dataLabeling.toString(), "/" + user);
            if (!userDir.exists()) {
                userDir.mkdir();
            }
            File userDirMode = new File(userDir.toString(), "/Costraint");
            if (!userDirMode.exists()) {
                userDirMode.mkdir();
            }


            File userDirExercise = new File(userDirMode.toString(), "/" + descProtocol);
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


            outputFileConstr = new File(userDirExerciseDayHour.toString(), "mydata.csv");

            outputFileConstr.createNewFile();


            final PrintWriter writer = new PrintWriter(new FileWriter(outputFileConstr.toString()));


            total.append(toOctalString(nowStart));


            writer.write(total.toString());

            CountDownTimer timer = new CountDownTimer(exercises.get(i).getDurata() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    // Toast.makeText(mContext, "Sto contando....." + toOctalString(getItemCount()), Toast.LENGTH_SHORT).show();
                    if (millisUntilFinished > 0) {
                        //buttonStartConst.setClickable(false);


                    }
                }

                @Override
                public void onFinish() {
                    Calendar calendar = Calendar.getInstance();
                    long nowFinish = calendar.getTimeInMillis();

                    total.append(",");
                    total.append(toOctalString(nowFinish));
                    total.append(",");

                    writer.write(total.toString());
                    writer.close();
                    Toast.makeText(mContext, "ho finito" + toOctalString(getItemCount()), Toast.LENGTH_LONG).show();
                    total.delete(0, total.length());
                    enableButton(i,holder);
                }
            }.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void enableButton(int tag,ViewHolder holder) {
        try {
            Button tempButton = (Button) holder.buttonStartConst.findViewWithTag(tag);

            int i = getItemViewType(tag);
            tempButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorToConfirm));

        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    public void disableButton(int tag) {

    }


    @Override
    public int getItemCount() {
        return exercises.size();
    }
}
