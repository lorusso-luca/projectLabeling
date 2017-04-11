package com.lorusso.luca.labeling;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Home extends AppCompatActivity {
    EditText id;
    Button loginID;
    private static final String TAG = "QuizActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.v(TAG, "onCreate() called");
    }
    @Override
    public void onStart(){
        super.onStart();
        Log.v(TAG, "onStart() called");
        id = (EditText) findViewById(R.id.idText);
        loginID = (Button) findViewById(R.id.buttonLogin);

        loginID.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String idUser = id.getText().toString();
                if(idUser.equals("")){
                    Toast.makeText(Home.this, "The field is empty! Please insert your personal ID.", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(Home.this, Mode.class);
                    i.putExtra("idUser", idUser);
                    startActivity(i);
                }

            }
        });

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

    }

}
