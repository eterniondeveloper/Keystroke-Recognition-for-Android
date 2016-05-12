package com.raptis.konstantinos.keystrokerecognitionforandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.raptis.konstantinos.keystrokerecognitionforandroid.components.CustomEditText;
import com.raptis.konstantinos.keystrokerecognitionforandroid.core.KeyHandler;

public class MainActivity extends AppCompatActivity {

    // variables
    public static KeyHandler keyHandler;

    // on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //------------------------------------------------------------------------------------------

        if(keyHandler == null) {
            keyHandler = new KeyHandler();
        }

        //------------------------------------------------------------------------------------------

        final CustomEditText usernameEditText = (CustomEditText) findViewById(R.id.usernameEditText);
        final CustomEditText passwordEditText = (CustomEditText) findViewById(R.id.passwordEditText);
        final Button loginButton = (Button) findViewById(R.id.loginButton);
    }

    // sign up
    public void signUp(View view) {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
    }

    // login
    /*public void login(View view) {
        User user = new User();
        user.setEmail("eternion@hotmail.com");
        user.setFirstName("Antonia");
        user.setLastName("Avtzi");
        user.setPassword("toor");
        new EndpointsAsyncTask().execute(new Pair<Context, User>(this, user));
    }*/

}
