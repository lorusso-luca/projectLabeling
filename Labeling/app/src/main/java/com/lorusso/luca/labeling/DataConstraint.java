package com.lorusso.luca.labeling;

import android.content.Intent;
import android.content.res.ObbInfo;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import static android.content.ContentValues.TAG;
import static java.lang.Long.toOctalString;

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

        completeConst.setEnabled(false);


        Protocol o = (Protocol) getIntent().getSerializableExtra("protocol");
        protocolDescription.setText(o.getDescrizione().toString());
        final String desc = o.getDescrizione();
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();

        exercises.addAll(o.getAttività());


        RecyclerView rvExercise = (RecyclerView) findViewById(R.id.my_recycler_view_exercise);

        ExerciseAdapter adapter = new ExerciseAdapter(this, exercises, completeConst) {
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
                i = new Intent(DataConstraint.this, Home.class);
                i.putExtra("user", user);
                startActivity(i);
                break;
            // action with ID action_settings was selected
            case R.id.action_open_explorel:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                        + "/DataLabeling/");
                intent.setDataAndType(uri, "text/csv/folder");
                startActivity(Intent.createChooser(intent, "Open folder"));
                break;
            case R.id.action_info:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                i = new Intent(DataConstraint.this, Info.class);
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
