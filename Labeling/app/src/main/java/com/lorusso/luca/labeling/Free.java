package com.lorusso.luca.labeling;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.ContentValues.TAG;
import static java.lang.Long.toOctalString;

public class Free extends AppCompatActivity {
    Button starFree;
    Button stopFree;
    Button completeFree;
    String user;
    Spinner spinner;
    String exercise;
    File outputFileFree;
    long nowStart;
    long nowFinish;
    ArrayList<Exercise> exercisesTotal = new ArrayList<Exercise>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user = getIntent().getStringExtra("user");
        spinner = (Spinner) findViewById(R.id.spinner);


    }

    public void onStart() {
        super.onStart();
        starFree = (Button) findViewById(R.id.buttonStartFree);

        starFree.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                nowStart = calendar.getTimeInMillis();
                starFree.setBackgroundColor(getResources().getColor(R.color.colorToConfirm));
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                String today = dateFormat.format(calendar.getTime());
                dateFormat.applyPattern("HH:mm.ss");
                String time = dateFormat.format(calendar.getTime());


                spinner.setEnabled(false);
                try {
                    File dataLabeling = new File(Environment.getExternalStorageDirectory()
                            + "/DataLabeling");
                    if (!dataLabeling.exists())
                        dataLabeling.mkdir();

                    File userDir = new File(dataLabeling.toString(), "/" + user);
                    if (!userDir.exists()) {
                        userDir.mkdir();
                    }
                    File userDirMode = new File(userDir.toString(), "/Free");
                    if (!userDirMode.exists()) {
                        userDirMode.mkdir();
                    }

                    exercise = spinner.getSelectedItem().toString();

                    File userDirExercise = new File(userDirMode.toString(), "/" + exercise);
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


                    outputFileFree = new File(userDirExerciseDayHour.toString(), "mydata.csv");

                    outputFileFree.createNewFile();


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        stopFree = (Button) findViewById(R.id.buttonStop);
        stopFree.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                nowFinish = calendar.getTimeInMillis();
                if (nowStart == 0) {
                    Toast.makeText(Free.this, "Tap on Button start before! ", Toast.LENGTH_LONG).show();
                } else {
                    stopFree.setBackgroundColor(getResources().getColor(R.color.colorToConfirm));
                    StringBuilder temp = new StringBuilder();
                    temp.append("Start");
                    temp.append(",");
                    temp.append("Stop");
                    temp.append("\n");
                    temp.append(toOctalString(nowStart));
                    temp.append(",");
                    temp.append(toOctalString(nowFinish));
                    temp.append(",");
                    temp.append(exercise);
                    Toast.makeText(Free.this, toOctalString((int)((nowFinish-nowStart)/1000)), Toast.LENGTH_LONG).show();


                    try {
                        PrintWriter writer = new PrintWriter(new FileWriter(outputFileFree.toString()));

                        writer.write(temp.toString());
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Exercise e = new Exercise(exercise, (int) ((nowFinish - nowStart) / 1000));
                    exercisesTotal.add(e);

                    RecyclerView rvExercise = (RecyclerView) findViewById(R.id.recyclerViewExercise);

                    FreeAdapter adapter = new FreeAdapter(Free.this, exercisesTotal) {
                        public void iconTextViewOnClick(View v, int position) {
                            Log.d(TAG, "iconTextViewOnClick at position " + position);
                            final Intent i = new Intent(Free.this, FreeAdapter.class);
                            startActivity(i);
                        }
                    };

                    rvExercise.setAdapter(adapter);

                    rvExercise.setLayoutManager(new LinearLayoutManager(Free.this));

                    Toast.makeText(Free.this, exercisesTotal.toString(), Toast.LENGTH_LONG).show();
                    adapter.notifyData(exercisesTotal);
                    starFree.setClickable(true);
                    starFree.setBackgroundColor(getResources().getColor(R.color.colorButtonStart));
                    stopFree.setBackgroundColor(getResources().getColor(R.color.colorButtonStop));
                    spinner.setEnabled(true);

                }

            }
        });

        completeFree = (Button) findViewById(R.id.buttonCompleteFree);

        completeFree.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Free.this, Mode.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("idUser", user);
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }


}
