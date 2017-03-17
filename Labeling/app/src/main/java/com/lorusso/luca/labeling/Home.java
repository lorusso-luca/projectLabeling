package com.lorusso.luca.labeling;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Home extends AppCompatActivity {
    EditText id;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        id = (EditText) findViewById(R.id.idText);
        login = (Button) findViewById(R.id.loginButton);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String idUser = id.getText().toString();
                Intent i = new Intent(Home.this,Mode.class);

               // SharedPreferences sharedpreferences = getSharedPreferences("User", Context.MODE_WORLD_READABLE);
                //SharedPreferences.Editor editor = sharedpreferences.edit();
                //editor.putString("key", idUser);
                //editor.commit();

                startActivity(i);

            }
        });

    }
}
