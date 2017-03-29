package com.lorusso.luca.labeling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class Free extends AppCompatActivity {
    Button starFree;
    Button stopFree;
    Button completeFree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        starFree = (Button) findViewById(R.id.buttonStart);


        stopFree = (Button) findViewById(R.id.buttonStop);


        completeFree = (Button) findViewById(R.id.buttonCompleteFree);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }
}
