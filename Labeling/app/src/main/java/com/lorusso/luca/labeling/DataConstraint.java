package com.lorusso.luca.labeling;

import android.content.Intent;
import android.content.res.ObbInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class DataConstraint extends AppCompatActivity {
    TextView temp;
    TextView protocolDescription;
    Button completeConst;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_constraint);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user = getIntent().getStringExtra("user");
        // temp = (TextView) findViewById(R.id.tempText);
        protocolDescription = (TextView) findViewById(R.id.textProtocolDesc);
        completeConst = (Button) findViewById(R.id.buttonCompleteCons);


        completeConst.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(DataConstraint.this, Constraint.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        Protocol o = (Protocol) getIntent().getSerializableExtra("protocol");
        protocolDescription.setText(o.getDescrizione().toString());
        final String desc = o.getDescrizione();
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();

        exercises.addAll(o.getAttività());


        RecyclerView rvExercise = (RecyclerView) findViewById(R.id.my_recycler_view_exercise);

        ExerciseAdapter adapter = new ExerciseAdapter(this, exercises) {
            public void iconTextViewOnClick(View v, int position) {
                Log.d(TAG, "iconTextViewOnClick at position " + position);
                final Intent i = new Intent(DataConstraint.this, ExerciseAdapter.class);
                i.putExtra("descProtocol", desc);
                i.putExtra("idUser", user);
                startActivity(i);
            }

        };
        rvExercise.setAdapter(adapter);

        rvExercise.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
