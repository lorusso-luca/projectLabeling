package com.lorusso.luca.labeling;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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


public class Free extends AppCompatActivity {
    Button starFree;
    Button stopFree;
    Button completeFree;
    String user;
    ProgressBar progressBar;
    Spinner spinner;
    String exercise;
    File outputFileFree;
    long nowStart;
    long nowFinish;
    ArrayList<Exercise> exercisesTotal = new ArrayList<Exercise>();
    String today;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user = getIntent().getStringExtra("user");
        spinner = (Spinner) findViewById(R.id.spinner);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


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

                today = dateFormat.format(calendar.getTime());
                dateFormat.applyPattern("HH:mm.ss");
                String time = dateFormat.format(calendar.getTime());

                completeFree.setEnabled(false);
                spinner.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);

                Intent intentNoti = new Intent();
                PendingIntent pIntent = PendingIntent.getActivity(Free.this, 0, intentNoti, 0);
                Notification noti = new Notification.Builder(Free.this)
                        .setTicker("TickerTitle")
                        .setContentTitle("Content Title")
                        .setContentText("Content Text ahjdsafd")
                        .setSmallIcon(R.drawable.imgnotification)
                        .setContentIntent(pIntent).getNotification();
                noti.flags = Notification.FLAG_AUTO_CANCEL;
                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                nm.notify(0, noti);


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
                    temp.append(user.toString() + "," + "Free" + "," + today);
                    temp.append("\n");
                    temp.append("Start");
                    temp.append(",");
                    temp.append("Stop");
                    temp.append("\n");
                    temp.append(String.valueOf(nowStart));
                    temp.append(",");
                    temp.append(String.valueOf(nowFinish));
                    temp.append(",");
                    temp.append(exercise);

                    try {
                        PrintWriter writer = new PrintWriter(new FileWriter(outputFileFree.toString()));
                        writer.write(temp.toString());
                        writer.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Exercise e = new Exercise(exercise, (int) ((nowFinish - nowStart) / 1000));
                    exercisesTotal.add(e);

                    progressBar.setVisibility(View.INVISIBLE);

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
                    completeFree.setEnabled(true);

                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.cancel(0);

                }

            }
        });

        completeFree = (Button) findViewById(R.id.buttonCompleteFree);

        completeFree.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Free.this, Home.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("user", user);
                startActivity(i);
            }
        });


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuinfo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_Home:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT)
                        .show();
                i = new Intent(Free.this, Home.class);
                i.putExtra("user", user);
                startActivity(i);
                break;
            // action with ID action_settings was selected
            case R.id.action_open_explorel:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
               /* Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                        + "/DataLabeling/");
                intent.setDataAndType(uri, "text/csv/folder");
                startActivity(Intent.createChooser(intent, "Open folder"));*/

                break;
            case R.id.action_info:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                i = new Intent(Free.this, Info.class);
                startActivity(i);
                break;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                break;
        }

        return true;
    }


}
