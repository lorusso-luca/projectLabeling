package com.lorusso.luca.labeling;

import android.content.res.Resources;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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


public class Constraint extends AppCompatActivity {


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint);


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
                    String durataExercise = (String)rowExer.get("durata");

                    Exercise ex = new Exercise(descExercise, Integer.parseInt(durataExercise));
                    e.add(ex);
                }

                Protocol p = new Protocol(id, nome, descrizione, e);
                protocols.add(p);
            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
