package com.raptis.konstantinos.keystrokerecognitionforandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.raptis.konstantinos.keystrokerecognitionforandroid.db.dto.User;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Helper;

/**
 * Created by konstantinos on 10/5/2016.
 */
public class SignUpActivity extends AppCompatActivity {

    private EditText signUpEmailEditText;
    private EditText signUpPasswordEditText;
    private EditText signUpFirstNameEditText;
    private EditText signUpLastNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signUpEmailEditText = (EditText) findViewById(R.id.signUpEmailEditText);
        signUpPasswordEditText = (EditText) findViewById(R.id.signUpPasswordEditText);
        signUpFirstNameEditText = (EditText) findViewById(R.id.signUpFirstNameEditText);
        signUpLastNameEditText = (EditText) findViewById(R.id.signUpLastNameEditText);
    }

    public void submit(View view) {
        if (!Helper.isEmpty(signUpEmailEditText) && !Helper.isEmpty(signUpPasswordEditText) &&
                !Helper.isEmpty(signUpFirstNameEditText) && !Helper.isEmpty(signUpLastNameEditText)) {
            User user = new User();
            user.setEmail(signUpEmailEditText.getText().toString());
            user.setPassword(signUpPasswordEditText.getText().toString());
            user.setFirstName(signUpFirstNameEditText.getText().toString());
            user.setLastName(signUpLastNameEditText.getText().toString());

            Intent i = new Intent(this, TrainingActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("user", user);
            startActivity(i);
        } else {
            Toast.makeText(SignUpActivity.this, Helper.NOT_ALL_FIELDS_FILLED, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

}
