package com.raptis.konstantinos.keystrokerecognitionforandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.raptis.konstantinos.keystrokerecognitionforandroid.classification.WekaConnector;
import com.raptis.konstantinos.keystrokerecognitionforandroid.components.CustomEditText;
import com.raptis.konstantinos.keystrokerecognitionforandroid.core.Buffer;
import com.raptis.konstantinos.keystrokerecognitionforandroid.core.KeyFactory;
import com.raptis.konstantinos.keystrokerecognitionforandroid.db.dto.User;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Helper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by konstantinos on 13/5/2016.
 */
public class TrainingActivity extends FragmentActivity {

    // variables
    private User user;
    private CustomEditText tre1;
    private CustomEditText tre2;
    private CustomEditText tre3;
    private CustomEditText tre4;
    private CustomEditText tre5;
    private CustomEditText tre6;
    private CustomEditText tre7;
    private CustomEditText tre8;
    private CustomEditText tre9;
    private CustomEditText tre10;
    private ArrayList<CustomEditText> bag;

    // on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        // get user
        user = (User) getIntent().getSerializableExtra("user");

        // get training editTexts
        tre1 = (CustomEditText) findViewById(R.id.trainingPasswordEdiText1);
        tre2 = (CustomEditText) findViewById(R.id.trainingPasswordEdiText2);
        tre3 = (CustomEditText) findViewById(R.id.trainingPasswordEdiText3);
        tre4 = (CustomEditText) findViewById(R.id.trainingPasswordEdiText4);
        tre5 = (CustomEditText) findViewById(R.id.trainingPasswordEdiText5);
        tre6 = (CustomEditText) findViewById(R.id.trainingPasswordEdiText6);
        tre7 = (CustomEditText) findViewById(R.id.trainingPasswordEdiText7);
        tre8 = (CustomEditText) findViewById(R.id.trainingPasswordEdiText8);
        tre9 = (CustomEditText) findViewById(R.id.trainingPasswordEdiText9);
        tre10 = (CustomEditText) findViewById(R.id.trainingPasswordEdiText10);

        bag = new ArrayList<>();

        // add all editTexts to bag
        bag.add(tre1);
        bag.add(tre2);
        bag.add(tre3);
        bag.add(tre4);
        bag.add(tre5);
        bag.add(tre6);
        bag.add(tre7);
        bag.add(tre8);
        bag.add(tre9);
        bag.add(tre10);

        // init editTexts
        for(CustomEditText cmEditText : bag) {
            cmEditText.init(user);
        }
    }

    // train button click
    public void train(View view) {
        if(tre1.isReady() && tre2.isReady() && tre3.isReady() && tre4.isReady() && tre5.isReady() &&
                tre6.isReady() && tre7.isReady() && tre8.isReady() && tre9.isReady() && tre10.isReady()) {
            // if all editTexts are ready then train
            //--------------------------------------------------------------------------------------

            WekaConnector wekaConnector = null;

            for(CustomEditText cmEditText : bag) {
                Buffer currentBuffer = cmEditText.getKeyHandler().getBuffer();
                double tempFSE = KeyFactory.getFSE(currentBuffer);
                double[] tempFDD = KeyFactory.getFDD(currentBuffer);
                double[] tempFUU = KeyFactory.getFUU(currentBuffer);
                double[] tempFUD = KeyFactory.getFUD(currentBuffer);
                double[] tempFD = KeyFactory.getFD(currentBuffer);
                double tempFAHT = KeyFactory.getFAHT(currentBuffer);

                if(wekaConnector == null) {
                    wekaConnector = new WekaConnector(tempFDD.length, tempFUU.length,
                            tempFUD.length, tempFD.length);
                }

                wekaConnector.train(tempFSE, tempFDD, tempFUU, tempFUD, tempFD, tempFAHT,
                        cmEditText.getKeyHandler().getErrorRateCounter());
            }

            try {
                boolean result = wekaConnector.saveToARFF(getApplicationContext());
                result = MainActivity.connector.addUser(user);
                Log.i(Helper.OUTPUT_LOG, "ARFF saved: " + result);
            } catch (IOException e) {
                Log.i(Helper.OUTPUT_LOG, e.getMessage());
            }
            wekaConnector.displayTrainingSet();

            //--------------------------------------------------------------------------------------

            Intent i = new Intent(TrainingActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else {
            Toast.makeText(TrainingActivity.this, Helper.NOT_ALL_FIELDS_FILLED_CORRECTLY, Toast.LENGTH_LONG).show();
        }
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TrainingActivity.this);
        builder.setMessage(Helper.CONFIRM_MESSAGE)
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show();
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int option) {
            switch (option) {
                case DialogInterface.BUTTON_POSITIVE:
                    Intent i = new Intent(TrainingActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

}
