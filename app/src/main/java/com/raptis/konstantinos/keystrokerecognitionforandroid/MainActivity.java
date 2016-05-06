package com.raptis.konstantinos.keystrokerecognitionforandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
