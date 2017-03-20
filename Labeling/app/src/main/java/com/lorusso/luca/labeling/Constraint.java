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


            Object obj = parser.parse(String.valueOf(in_s));
            JSONArray protocolli = (JSONArray) obj;
            File file = Environment.getDataDirectory().getAbsoluteFile();
            int id = protocolli.size();

          //  String name = (String) jsonObject.get("Name");
           // String author = (String) jsonObject.get("Author");
            //JSONArray companyList = (JSONArray) jsonObject.get("Company List");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
