package com.raptis.konstantinos.keystrokerecognitionforandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.raptis.konstantinos.keystrokerecognitionforandroid.classification.WekaConnector;
import com.raptis.konstantinos.keystrokerecognitionforandroid.components.CustomPasswordEditText;
import com.raptis.konstantinos.keystrokerecognitionforandroid.core.Buffer;
import com.raptis.konstantinos.keystrokerecognitionforandroid.core.KeyFactory;
import com.raptis.konstantinos.keystrokerecognitionforandroid.db.dto.User;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Helper;

import java.io.IOException;

import weka.core.Instances;

/**
 * Created by konstantinos on 20/5/2016.
 */
public class TestingActivity extends AppCompatActivity {

    // variables
    private User user;
    private CustomPasswordEditText passwordEditText;

    // on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        // get user
        user = (User) getIntent().getSerializableExtra("user");

        passwordEditText = (CustomPasswordEditText) findViewById(R.id.passwordEditText);

        passwordEditText.init(user);
    }

    public void login(View view) {
        if(user.getPassword().equals(passwordEditText.getText().toString())) {
            try {
                Instances trainingSet = WekaConnector.loadARFF(getApplicationContext(), user.getArffName());

                Buffer currentBuffer = passwordEditText.getKeyHandler().getBuffer();
                double tempFSE = KeyFactory.getFSE(currentBuffer);
                double[] tempFDD = KeyFactory.getFDD(currentBuffer);
                double[] tempFUU = KeyFactory.getFUU(currentBuffer);
                double[] tempFUD = KeyFactory.getFUD(currentBuffer);
                double[] tempFD = KeyFactory.getFD(currentBuffer);
                double tempFAHT = KeyFactory.getFAHT(currentBuffer);

                WekaConnector wekaConnector = new WekaConnector(tempFDD.length, tempFUU.length,
                        tempFUD.length, tempFD.length);

                wekaConnector.setInstances(tempFSE, tempFDD, tempFUU, tempFUD, tempFD, tempFAHT,
                        passwordEditText.getKeyHandler().getErrorRateCounter(), null);

                Instances testingSet = wekaConnector.getInstancesSet();

                //wekaConnector.displayInstancesSet();

                try {
                    boolean result = WekaConnector.test(trainingSet, testingSet);
                    Log.i(Helper.RESULT_LOG, "User login: " + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Log.i(Helper.RESULT_LOG, trainingSet.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(TestingActivity.this, Helper.INVALID_PASSWORD, Toast.LENGTH_LONG).show();
        }
    }

}
