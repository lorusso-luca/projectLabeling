package com.lorusso.luca.labeling;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Free extends AppCompatActivity {
    Button starFree;
    Button stopFree;
    Button completeFree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void onStart() {
        super.onStart();
        starFree = (Button) findViewById(R.id.buttonStartFree);

        starFree.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                File folder = new File(Environment.getExternalStorageDirectory()
                        + "/Folder");

                boolean var = false;
                if (!folder.exists())
                    var = folder.mkdir();

                System.out.println("" + var);

                File gpxfile = new File(folder.toString(), "mydata.csv");

                try {
                    gpxfile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //final String filename = folder.toString() + "/" + "Test.csv";
            }
        });

        stopFree = (Button) findViewById(R.id.buttonStop);


        completeFree = (Button) findViewById(R.id.buttonCompleteFree);

        completeFree.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Free.this, Mode.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

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
