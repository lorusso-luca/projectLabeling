package com.lorusso.luca.labeling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DataConstraint extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_constraint);
        getIntent().getSerializableExtra("protocol");
        String nome = "ciao";

    }
}
