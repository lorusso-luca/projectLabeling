package com.lorusso.luca.labeling;


import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;


public class Mode extends AppCompatActivity {

    RadioButton radioLibero;
    RadioButton radioVincolato;
    Button buttonMode;
    String user;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        radioLibero = (RadioButton) findViewById(R.id.radioLibero);
        radioVincolato = (RadioButton) findViewById(R.id.radioVincolato);
        buttonMode = (Button) findViewById(R.id.buttonMode);
        user = getIntent().getStringExtra("idUser");

        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkPermission())
            {
                // Code for above or equal 23 API Oriented Device
                // Your Permission granted already .Do next code
            } else {
                requestPermission(); // Code for permission
            }
        }
        else
        {

            // Code for Below 23 API Oriented Device
            // Do next code
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(Mode.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(Mode.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(Mode.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(Mode.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioVincolato:
                if (checked)
                    buttonMode.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            Intent i = new Intent(Mode.this, Constraint.class);
                            i.putExtra("user", user);
                            startActivity(i);
                        }
                    });
                break;
            case R.id.radioLibero:
                if (checked)
                    buttonMode.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            Intent i = new Intent(Mode.this, Free.class);
                            i.putExtra("user", user);
                            startActivity(i);

                        }
                    });
                break;
        }
    }
}
