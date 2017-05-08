package com.lorusso.luca.labeling;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static android.content.res.ColorStateList.*;
import static com.lorusso.luca.labeling.R.color.colorToConfirm;
import static com.lorusso.luca.labeling.R.id.action_bar;
import static com.lorusso.luca.labeling.R.id.action_mode_bar_stub;
import static com.lorusso.luca.labeling.R.id.buttonStartConst;
import static com.lorusso.luca.labeling.R.id.my_recycler_view_exercise;
import static java.lang.Long.toOctalString;

/**
 * Created by Luca on 30/03/2017.
 */

public class ExerciseAdapter extends
        RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView textExercise;
        public TextView textDurata;
        public Button buttonStartConst;
        public Button buttonRestart;
        public ProgressBar progressBar;


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

            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBarCos);
        }

    }


    private List<Exercise> exercises;
    // Store the context for easy access
    private Context mContext;
    String timeStart;
    ArrayList<ViewHolder> vh = new ArrayList<ViewHolder>();
    Button b;
    CountDownTimer timer;
    File outputFileConstr = null;
    String finalPath = null;
    int count = 0;
    String user;

    public ExerciseAdapter(Context mContext, ArrayList<Exercise> exercises, Button b) {
        this.exercises = exercises;
        this.mContext = mContext;
        this.b = b;
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
        vh.add(holder);

        final Exercise exercise = exercises.get(position);

        holder.textExercise.setText(exercise.getEsercizio());

        holder.itemView.setTag(position);

        holder.textDurata.setText("Durata: " + exercise.getDurata());

        holder.buttonStartConst.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorButtonStart));
        holder.buttonRestart.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorToHide));
        holder.buttonRestart.setClickable(false);

        holder.buttonStartConst.setTag(position);
        holder.buttonRestart.setTag(position);
        holder.textDurata.setTag(position);
        holder.textExercise.setTag(position);
        holder.progressBar.setTag(position);


        if (position != 0) {

            Button tempStart = (Button) holder.buttonStartConst.findViewWithTag(position);
            tempStart.setEnabled(false);
            Button tempRestart = (Button) holder.buttonRestart.findViewWithTag(position);
            tempRestart.setEnabled(false);

        }
        holder.buttonStartConst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.buttonRestart.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorButtonStop));
                holder.buttonRestart.setEnabled(true);

                doAction((Integer) v.getTag(), holder);
                holder.buttonRestart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "Restart", Toast.LENGTH_SHORT).show();
                        if (timer != null) {
                            timer.cancel();
                            count = 0;
                        }
                        holder.buttonStartConst.setClickable(true);
                        holder.buttonRestart.setClickable(false);
                        holder.buttonRestart.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorToHide));
                    }
                });
            }
        });

    }

    public StringBuilder total = new StringBuilder();

    public void doAction(int i, ViewHolder holder) {

        if (i == 0) {
            createFile(i, holder);
        } else if (i >= 1 && i < exercises.size() - 1) {
            buildString(i, holder);
        } else {
            createFinalFile(i, holder);
        }


    }


    public void createFile(final int i, final ViewHolder holder) {

        Calendar calendar = Calendar.getInstance();
        final long nowStart = calendar.getTimeInMillis();
        user = ((Activity) mContext).getIntent().getStringExtra("user");
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

            finalPath = userDirExerciseDayHour.toString();

            outputFileConstr = new File(finalPath, "mydata.csv");

            outputFileConstr.createNewFile();
            final ProgressBar tempProgress = (ProgressBar) holder.progressBar.findViewWithTag(i);
            tempProgress.setMax(exercises.get(i).getDurata());

            if (Build.VERSION.SDK_INT >= 21) {
                tempProgress.setProgressTintList(ColorStateList.valueOf(Color.BLUE));
            }
            timer = new CountDownTimer(exercises.get(i).getDurata() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (millisUntilFinished > 0) {
                        holder.buttonRestart.setClickable(true);
                        holder.buttonStartConst.setClickable(false);
                        holder.textDurata.setClickable(false);
                        holder.textExercise.setClickable(false);

                        count++;
                        tempProgress.setProgress(count);

                    }
                }

                @Override
                public void onFinish() {
                    Calendar calendar = Calendar.getInstance();
                    long nowFinish = calendar.getTimeInMillis();
                    total.append(user.toString()+","+"Free"+","+today);
                    total.append("\n");
                    total.append("Start");
                    total.append(",");
                    total.append("Stop");
                    total.append("\n");
                    total.append(nowStart);
                    total.append(",");
                    total.append(String.valueOf(nowFinish));
                    total.append(",");
                    total.append(exercises.get(i).getEsercizio());
                    count++;
                    tempProgress.setProgress(count);
                    Toast.makeText(mContext, "ho finito" + toOctalString(getItemCount()), Toast.LENGTH_LONG).show();
                    count = 0;
                    enableButton(i, holder);
                    disableButton(i, holder);

                }
            }.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildString(final int i, final ViewHolder holder) {

        Calendar calendar = Calendar.getInstance();
        final long nowStart = calendar.getTimeInMillis();
        final ProgressBar tempProgress = (ProgressBar) holder.progressBar.findViewWithTag(i);
        tempProgress.setMax(exercises.get(i).getDurata());
        if (Build.VERSION.SDK_INT >= 21) {
            tempProgress.setProgressTintList(ColorStateList.valueOf(Color.BLUE));
        }

        timer = new CountDownTimer(exercises.get(i).getDurata() * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished > 0) {
                    holder.buttonRestart.setClickable(true);
                    holder.buttonStartConst.setClickable(false);
                    holder.textDurata.setClickable(false);
                    holder.textExercise.setClickable(false);
                    count++;
                    tempProgress.setProgress(count);

                }
            }

            @Override
            public void onFinish() {
                Calendar calendar = Calendar.getInstance();
                long nowFinish = calendar.getTimeInMillis();
                total.append("\n");
                total.append(nowStart);
                total.append(",");
                total.append(String.valueOf(nowFinish));
                total.append(",");
                total.append(exercises.get(i).getEsercizio());
                count++;
                tempProgress.setProgress(count);
                Toast.makeText(mContext, "ho finito" + toOctalString(getItemCount()), Toast.LENGTH_LONG).show();
                count = 0;
                enableButton(i, holder);
                disableButton(i, holder);

            }
        }.start();

    }

    private void createFinalFile(final int i, final ViewHolder holder) {

        Calendar calendar = Calendar.getInstance();
        final long nowStart = calendar.getTimeInMillis();
        outputFileConstr = new File(finalPath, "mydata.csv");
        final ProgressBar tempProgress = (ProgressBar) holder.progressBar.findViewWithTag(i);
        tempProgress.setMax(exercises.get(i).getDurata());
        if (Build.VERSION.SDK_INT >= 21) {
            tempProgress.setProgressTintList(ColorStateList.valueOf(Color.BLUE));
        }
        timer = new CountDownTimer(exercises.get(i).getDurata() * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished > 0) {
                    holder.buttonRestart.setClickable(true);
                    holder.buttonStartConst.setClickable(false);
                    holder.textDurata.setClickable(false);
                    holder.textExercise.setClickable(false);
                    count++;
                    tempProgress.setProgress(count);
                }
            }

            @Override
            public void onFinish() {
                Calendar calendar = Calendar.getInstance();
                long nowFinish = calendar.getTimeInMillis();
                total.append("\n");
                total.append(nowStart);
                total.append(",");
                total.append(String.valueOf(nowFinish));
                total.append(",");
                total.append(exercises.get(i).getEsercizio());
                count++;
                tempProgress.setProgress(count);
                Toast.makeText(mContext, "ho finito" + toOctalString(getItemCount()), Toast.LENGTH_LONG).show();
                enableButton(i, holder);
                disableButton(i, holder);
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(new FileWriter(outputFileConstr.toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                }


                writer.write(total.toString());
                writer.close();
                b.setEnabled(true);
                b.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                b.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
                b.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, Constraint.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("user", user);
                        mContext.startActivity(i);
                    }
                });
            }
        }.start();


    }

    public void enableButton(int tag, ViewHolder holder) {
        try {

            ViewHolder viewHolder = vh.get(tag + 1);
            Button tempButton = (Button) viewHolder.buttonStartConst.findViewWithTag(tag + 1);
            tempButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorButtonStart));
            tempButton.setEnabled(true);

            TextView textViewDurata = (TextView) viewHolder.textDurata.findViewWithTag(tag + 1);
            textViewDurata.setTextColor(R.color.colorAccent);

            TextView textViewExercise = (TextView) viewHolder.textExercise.findViewWithTag(tag + 1);
            textViewExercise.setTextColor(R.color.colorAccent);


        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    public void disableButton(int tag, ViewHolder holder) {
        try {
            Button tempButtonStart = (Button) holder.buttonStartConst.findViewWithTag(tag);
            tempButtonStart.setEnabled(false);
            tempButtonStart.setBackgroundColor(ContextCompat.getColor(mContext, colorToConfirm));
            Button tempButtonRestart = (Button) holder.buttonRestart.findViewWithTag(tag);
            tempButtonRestart.setEnabled(false);
            tempButtonRestart.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorToHide));
            holder.textDurata.setTextColor(ContextCompat.getColor(mContext, R.color.colorToHide));
            holder.textExercise.setTextColor(ContextCompat.getColor(mContext, R.color.colorToHide));

        } catch (Exception e) {
            e.getStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return exercises.size();
    }
}
