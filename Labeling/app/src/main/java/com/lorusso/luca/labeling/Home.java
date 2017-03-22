package com.lorusso.luca.labeling;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;


public class Home extends AppCompatActivity {
    EditText id;
    Button loginID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        id = (EditText) findViewById(R.id.idText);
        loginID = (Button) findViewById(R.id.buttonLogin);

        loginID.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String idUser = id.getText().toString();
                Intent i = new Intent(Home.this, Mode.class);
                i.putExtra("idUser", idUser);
                startActivity(i);
            }
        });

    }
}
