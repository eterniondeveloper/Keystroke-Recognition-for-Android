package com.raptis.konstantinos.keystrokerecognitionforandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // login
    public void test(View view) {
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.userRadioGroup);

        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();

        if (!passwordEditText.getText().toString().equals("")) {

            Intent i = new Intent(MainActivity.this, ExtractFeaturesActivity.class);
            i.putExtra("password", passwordEditText.getText().toString());

            if (checkedRadioButtonId == R.id.positiveRadioButton) {
                i.putExtra("user", true);
                startActivity(i);
            } else if (checkedRadioButtonId == R.id.negativeRadioButton) {
                i.putExtra("user", false);
                startActivity(i);
            } else {
                Toast.makeText(MainActivity.this, "Please choose a radio button option!!!", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(MainActivity.this, "Password field is empty, type a password!!!", Toast.LENGTH_LONG).show();
        }

    }

}
