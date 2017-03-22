package com.lorusso.luca.labeling;

import android.content.res.ObbInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

public class DataConstraint extends AppCompatActivity {
TextView temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_constraint);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        temp = (TextView) findViewById(R.id.tempText);

        Object o = getIntent().getSerializableExtra("protocol");

        temp.setText(o.toString());


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
