package com.lorusso.luca.labeling;

import android.content.res.Resources;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class Constraint extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        JSONParser parser = new JSONParser();


        try {
            Resources res = getResources();
            InputStream in_s = res.openRawResource(R.raw.protocolli);

            byte[] b = new byte[in_s.available()];
            in_s.read(b);

            String str = new String(b, "UTF-8");
            Object obj = parser.parse(str);

            JSONObject protocolli = (JSONObject) obj;

            JSONArray protocolliArray = (JSONArray) protocolli.get("protocolli");

            ArrayList<Protocol> protocols = new ArrayList<Protocol>();

            for (int i = 0; i < protocolliArray.size(); i++) {
                JSONObject row = (JSONObject) protocolliArray.get(i);
                String nome = (String) row.get("nome");
                String id = (String) row.get("id");
                String descrizione = (String) row.get("descrizione");

                JSONArray exer = (JSONArray) row.get("Exercise");
                ArrayList<Exercise> e = new ArrayList<Exercise>();

                for (int j = 0; j < exer.size(); j++) {
                    JSONObject rowExer = (JSONObject) exer.get(j);
                    String descExercise = (String) rowExer.get("descrizione");
                    String durataExercise = (String) rowExer.get("durata");

                    Exercise ex = new Exercise(descExercise, Integer.parseInt(durataExercise));
                    e.add(ex);
                }

                Protocol p = new Protocol(id, nome, descrizione, e);
                protocols.add(p);
            }

            RecyclerView rvProtocol = (RecyclerView) findViewById(R.id.my_recycler_view);

            // Initialize contacts
            //temp = Person.createContactsList(temp.size());
            // Create adapter passing in the sample user data
            ProtocolAdapter adapter = new ProtocolAdapter(this, protocols) {
                public void iconTextViewOnClick(View v, int position) {
                    Log.d(TAG, "iconTextViewOnClick at position " + position);
                }
            };


            rvProtocol.setAdapter(adapter);
            // Set layout manager to position the items
            rvProtocol.setLayoutManager(new LinearLayoutManager(this));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
