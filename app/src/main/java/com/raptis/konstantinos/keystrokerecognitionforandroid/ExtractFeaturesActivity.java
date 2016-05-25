package com.raptis.konstantinos.keystrokerecognitionforandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.raptis.konstantinos.keystrokerecognitionforandroid.classification.WekaObject;
import com.raptis.konstantinos.keystrokerecognitionforandroid.components.CustomEditText;
import com.raptis.konstantinos.keystrokerecognitionforandroid.core.Buffer;
import com.raptis.konstantinos.keystrokerecognitionforandroid.core.KeyFactory;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Helper;

import java.util.ArrayList;

/**
 * Created by konstantinos on 25/5/2016.
 */
public class ExtractFeaturesActivity extends AppCompatActivity {

    // variables
    private CustomEditText extract1;
    private CustomEditText extract2;
    private CustomEditText extract3;
    private CustomEditText extract4;
    private CustomEditText extract5;
    private ArrayList<CustomEditText> bag;
    private String password;
    private boolean user;

    // on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract_features);

        Intent i = getIntent();

        password = i.getStringExtra("password");
        user = i.getBooleanExtra("user", false);

        // get training editTexts
        extract1 = (CustomEditText) findViewById(R.id.extractingPasswordEdiText1);
        extract2 = (CustomEditText) findViewById(R.id.extractingPasswordEdiText2);
        extract3 = (CustomEditText) findViewById(R.id.extractingPasswordEdiText3);
        extract4 = (CustomEditText) findViewById(R.id.extractingPasswordEdiText4);
        extract5 = (CustomEditText) findViewById(R.id.extractingPasswordEdiText5);

        bag = new ArrayList<>();

        // add all editTexts to bag
        bag.add(extract1);
        bag.add(extract2);
        bag.add(extract3);
        bag.add(extract4);
        bag.add(extract5);

        // init editTexts
        for(CustomEditText cmEditText : bag) {
            cmEditText.init(password);
        }
    }

    // train button click
    public void extract(View view) {
        if(extract1.isReady() && extract2.isReady() && extract3.isReady() && extract4.isReady() && extract5.isReady()) {
            // if all editTexts are ready then train
            //--------------------------------------------------------------------------------------

            WekaObject trainingSet = null;

            for(CustomEditText cmEditText : bag) {
                Buffer currentBuffer = cmEditText.getKeyHandler().getBuffer();
                double tempFSE = KeyFactory.getFSE(currentBuffer);
                double[] tempFDD = KeyFactory.getFDD(currentBuffer);
                double[] tempFUU = KeyFactory.getFUU(currentBuffer);
                double[] tempFUD = KeyFactory.getFUD(currentBuffer);
                double[] tempFD = KeyFactory.getFD(currentBuffer);
                double tempFAHT = KeyFactory.getFAHT(currentBuffer);

                if(trainingSet == null) {
                    trainingSet = new WekaObject(tempFDD.length, tempFUU.length,
                            tempFUD.length, tempFD.length);
                }

                if(user) {
                    trainingSet.setInstances(tempFSE, tempFDD, tempFUU, tempFUD, tempFD, tempFAHT,
                            cmEditText.getKeyHandler().getErrorRateCounter(), WekaObject.POSITIVE);
                } else {
                    trainingSet.setInstances(tempFSE, tempFDD, tempFUU, tempFUD, tempFD, tempFAHT,
                            cmEditText.getKeyHandler().getErrorRateCounter(), WekaObject.NEGATIVE);
                }
            }

            trainingSet.displayInstancesSet();

            //--------------------------------------------------------------------------------------

            // init editTexts
            for(CustomEditText cmEditText : bag) {
                cmEditText.init(password);
                cmEditText.setText("");
            }

        } else {
            Toast.makeText(ExtractFeaturesActivity.this, Helper.NOT_ALL_FIELDS_FILLED_CORRECTLY, Toast.LENGTH_LONG).show();
        }
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ExtractFeaturesActivity.this);
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
                    Intent i = new Intent(ExtractFeaturesActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

}
