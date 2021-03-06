package com.lorusso.luca.labeling;

import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.Toast;

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
    String user;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user = getIntent().getStringExtra("user");

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
                i = new Intent(Constraint.this, Home.class);
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
                i = new Intent(Constraint.this, Info.class);
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
