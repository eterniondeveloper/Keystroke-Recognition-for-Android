package com.raptis.konstantinos.keystrokerecognitionforandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.raptis.konstantinos.keystrokerecognitionforandroid.classification.WekaConnector;
import com.raptis.konstantinos.keystrokerecognitionforandroid.components.CustomEditText;
import com.raptis.konstantinos.keystrokerecognitionforandroid.db.DBConnector;
import com.raptis.konstantinos.keystrokerecognitionforandroid.db.dto.User;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Helper;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Key;

import java.io.IOException;

import weka.core.Instances;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener {

    // variables
    public static DBConnector connector;
    private EditText usernameEditText;
    private CustomEditText passwordEditText;

    // on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connector = new DBConnector(this, null, null, DBConnector.DATABASE_VERSION);

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (CustomEditText) findViewById(R.id.passwordEditText);

        usernameEditText.setOnKeyListener(this);
    }

    // sign up
    public void signUp(View view) {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
    }

    // login
    public void login(View view) {
        User user = MainActivity.connector.getUser(usernameEditText.getText().toString(),
                passwordEditText.getText().toString());

        if(user != null) {
            Log.i(Helper.USER_LOG, user.toString() + " successful login");

            try {
                Instances trainingSet = WekaConnector.loadARFF(getApplicationContext());
                Log.i(Helper.RESULT_LOG, trainingSet.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(Helper.USER_LOG, "unsuccessful login");
        }
    }


    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        Log.i(Helper.KEY_PRESSED, "Key Code" + keyEvent.getKeyCode());
        // If the event is a key-down event on the "enter" button
        if (keyEvent.getKeyCode() == Key.DONE_BUTTON.getPrimaryCode()) {
            // Perform action on key press
            User user = connector.getUser(usernameEditText.getText().toString());
            passwordEditText.init(user);
            return true;
        }
        return false;
    }
}
