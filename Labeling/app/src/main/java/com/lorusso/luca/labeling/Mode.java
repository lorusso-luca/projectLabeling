package com.lorusso.luca.labeling;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class Mode extends AppCompatActivity {
    TextView prova;
    RadioButton radioLibero;
    RadioButton radioVincolato;
    Button buttonMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        radioLibero = (RadioButton) findViewById(R.id.radioLibero);
        radioVincolato = (RadioButton) findViewById(R.id.radioVincolato);
        buttonMode = (Button) findViewById(R.id.buttonMode);
        Intent i = getIntent();


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
                            startActivity(i);
                        }
                    });
                break;
            case R.id.radioLibero:
                if (checked)
                    buttonMode.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            Intent i = new Intent(Mode.this, Home.class);

                            startActivity(i);

                        }
                    });
                break;
        }
    }
}
