package com.raptis.konstantinos.keystrokerecognitionforandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Helper;

public class MainActivity extends AppCompatActivity {

    // on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.loginFormLinearLayout);
        final EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        final EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        final Button loginButton = (Button) findViewById(R.id.loginButton);

        usernameEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == 87) {
                    passwordEditText.requestFocus();
                }
                return false;
            }
        });

        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == 87) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(MainActivity.this.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    passwordEditText.clearFocus();
                    usernameEditText.clearFocus();
                    linearLayout.requestFocus();
                }
                return false;
            }
        });
    }

    // sign up
    public void signUp(View view) {
        Toast.makeText(this, "Signing Up", Toast.LENGTH_SHORT).show();
    }

    // login
    public void login(View view) {
        Toast.makeText(this, "Logiging In", Toast.LENGTH_SHORT).show();
    }

}
