package com.raptis.konstantinos.keystrokerecognitionforandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.raptis.konstantinos.keystrokerecognitionforandroid.db.dto.User;

/**
 * Created by konstantinos on 26/5/2016.
 */
public class CreateUserActivity extends AppCompatActivity {

    // variables
    private EditText passwordEditText;

    // on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        setTitle("Create User");

        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
    }

    // add
    public void add(View view) {
        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);

        User user = new User();
        user.setUsername(usernameEditText.getText().toString());
        user.setPassword(passwordEditText.getText().toString());

        if (!user.getUsername().equals("") &&
                !user.getPassword().equals("")) {

            Intent i = new Intent(CreateUserActivity.this, ExtractFeaturesActivity.class);
            i.putExtra("user", user);
            startActivity(i);
            CreateUserActivity.this.finish();
        } else {
            Toast.makeText(CreateUserActivity.this, "Not all fields filled!!!", Toast.LENGTH_LONG).show();
        }

    }

    // show password
    public void showPassword(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        if(checked) {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        } else {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

    }

}
