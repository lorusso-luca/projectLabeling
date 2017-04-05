package com.lorusso.luca.labeling;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Free extends AppCompatActivity {
    Button starFree;
    Button stopFree;
    Button completeFree;
    String user;
    Spinner spinner;
    String exercise;

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
                starFree.setBackgroundColor(getResources().getColor(R.color.colorToConfirm));


                File dataLabeling = new File(Environment.getExternalStorageDirectory()
                        + "/DataLabeling");
                if (!dataLabeling.exists())
                    dataLabeling.mkdir();

                File userDir = new File(dataLabeling.toString(), "/" + user);
                if (!userDir.exists()) {
                    userDir.mkdir();
                }
                exercise = spinner.getSelectedItem().toString();
                File userDirExercise = new File(userDir.toString(), "/" + exercise);
                if (!userDirExercise.exists()) {
                    userDirExercise.mkdir();
                }

                File gpxfile = new File(userDirExercise.toString(), "mydata.csv");

                try {
                    gpxfile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        stopFree = (Button) findViewById(R.id.buttonStop);


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
