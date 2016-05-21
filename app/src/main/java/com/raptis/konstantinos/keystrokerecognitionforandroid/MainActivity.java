package com.raptis.konstantinos.keystrokerecognitionforandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.raptis.konstantinos.keystrokerecognitionforandroid.db.DBConnector;
import com.raptis.konstantinos.keystrokerecognitionforandroid.db.dto.User;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Helper;

public class MainActivity extends AppCompatActivity {

    // variables
    public static DBConnector connector;
    private EditText usernameEditText;

    // on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connector = new DBConnector(this, null, null, DBConnector.DATABASE_VERSION);
    }

    // sign up
    public void signUp(View view) {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
    }

    // login
    public void test(View view) {
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);

        User user = MainActivity.connector.getUser(usernameEditText.getText().toString());

        if(user != null) {
            Log.i(Helper.USER_LOG, user.toString() + " Exist in Database!");

            Intent i = new Intent(this, TestingActivity.class);
            i.putExtra("user", user);
            startActivity(i);
        } else {
            Log.i(Helper.USER_LOG, "unsuccessful login");
            Toast.makeText(MainActivity.this, Helper.INVALID_USERNAME, Toast.LENGTH_LONG);
        }
    }

}
