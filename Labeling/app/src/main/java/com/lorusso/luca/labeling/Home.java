package com.lorusso.luca.labeling;


import android.content.Intent;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class Home extends AppCompatActivity {
    EditText id;
    Button loginID;
    Spinner spinner;
    String mode;
    private static final int PERMISSION_REQUEST_CODE = 1;

    private static final String TAG = "QuizActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        spinner = (Spinner) findViewById(R.id.spinner2);
        id = (EditText) findViewById(R.id.idText);
        loginID = (Button) findViewById(R.id.buttonLogin);
        String user = getIntent().getStringExtra("user");

        id.setText(user);

        Log.v(TAG, "onCreate() called");
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                // Code for above or equal 23 API Oriented Device
                // Your Permission granted already .Do next code
            } else {
                requestPermission(); // Code for permission
            }
        } else {

            // Code for Below 23 API Oriented Device
            // Do next code
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG, "onStart() called");
        id = (EditText) findViewById(R.id.idText);
        loginID = (Button) findViewById(R.id.buttonLogin);

        loginID.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String user = id.getText().toString();
                if (user.equals("")) {
                    Toast.makeText(Home.this, "The field is empty! Please insert your personal ID.", Toast.LENGTH_LONG).show();
                } else {
                    mode = spinner.getSelectedItem().toString();
                    if (mode.equals("Costraint")) {
                        Intent i = new Intent(Home.this, Constraint.class);
                        i.putExtra("user", user);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(Home.this, Free.class);
                        i.putExtra("user", user);
                        startActivity(i);
                    }

                }

            }
        });

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(Home.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(Home.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(Home.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(Home.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuinfo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_Home:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT)
                        .show();
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
                Intent i = new Intent(Home.this, Info.class);
                startActivity(i);
                break;
            default:
                break;
        }

        return true;
    }

}
