package com.raptis.konstantinos.keystrokerecognitionforandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.raptis.konstantinos.keystrokerecognitionforandroid.classification.WekaConnector;
import com.raptis.konstantinos.keystrokerecognitionforandroid.classification.WekaObject;
import com.raptis.konstantinos.keystrokerecognitionforandroid.components.CustomEditText;
import com.raptis.konstantinos.keystrokerecognitionforandroid.core.Buffer;
import com.raptis.konstantinos.keystrokerecognitionforandroid.core.KeyFactory;
import com.raptis.konstantinos.keystrokerecognitionforandroid.db.dto.User;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Helper;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.TheClass;

import java.io.IOException;
import java.util.ArrayList;

import weka.core.Instances;

/**
 * Created by konstantinos on 27/5/2016.
 */
public class TrainingActivity extends AppCompatActivity {

    // variables
    private CustomEditText extract1;
    private CustomEditText extract2;
    private CustomEditText extract3;
    private CustomEditText extract4;
    private CustomEditText extract5;
    private ArrayList<CustomEditText> bag;
    private User user;
    private WekaObject set;
    private RadioGroup radioGroup;
    private Instances oldTraining;

    // on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        setTitle("Training User Model");

        radioGroup = (RadioGroup) findViewById(R.id.userRadioGroup);

        user = (User) getIntent().getSerializableExtra("user");

        // load old training set
        if (user != null) {
            try {
                oldTraining = WekaConnector.loadARFF(getApplicationContext(), user.getTrainArffName(), true);
                boolean isDeleted = WekaConnector.deleteARFF(getApplicationContext(), user.getTrainArffName(), true);
                Log.i(Helper.RESULT_LOG, "old arff deleted: " + isDeleted);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(Helper.DB_LOG, "User is null");
            return;
        }

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
            cmEditText.init(user.getPassword());
        }
    }

    // train button click
    public void extract(View view) {
        if(extract1.isReady() && extract2.isReady() && extract3.isReady() && extract4.isReady() && extract5.isReady()) {
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();

            // if all editTexts are ready then train
            //--------------------------------------------------------------------------------------

            for(CustomEditText cmEditText : bag) {
                Buffer currentBuffer = cmEditText.getKeyHandler().getBuffer();
                double tempFSE = KeyFactory.getFSE(currentBuffer);
                double[] tempFDD = KeyFactory.getFDD(currentBuffer);
                double[] tempFUU = KeyFactory.getFUU(currentBuffer);
                double[] tempFUD = KeyFactory.getFUD(currentBuffer);
                double[] tempFD = KeyFactory.getFD(currentBuffer);
                double tempFAHT = KeyFactory.getFAHT(currentBuffer);

                if(set == null) {
                    set = new WekaObject(tempFDD.length, tempFUU.length,
                            tempFUD.length, tempFD.length, user.getPassword());
                    // adding old training here
                    set.setInstancesSet(oldTraining);
                }

                if(checkedRadioButtonId == R.id.positiveRadioButton) {
                    set.addInstance(tempFSE, tempFDD, tempFUU, tempFUD, tempFD, tempFAHT,
                            cmEditText.getKeyHandler().getErrorRateCounter(), TheClass.POSITIVE);
                } else {
                    set.addInstance(tempFSE, tempFDD, tempFUU, tempFUD, tempFD, tempFAHT,
                            cmEditText.getKeyHandler().getErrorRateCounter(), TheClass.NEGATIVE);
                }
            }

            set.displayInstancesSet();

            //--------------------------------------------------------------------------------------

            // init editTexts
            for(CustomEditText cmEditText : bag) {
                cmEditText.init(user.getPassword());
                cmEditText.setText("");
            }

        } else {
            Toast.makeText(TrainingActivity.this, Helper.NOT_ALL_FIELDS_FILLED_CORRECTLY, Toast.LENGTH_LONG).show();
        }
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        // Get custom_input_dialog.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View dialogView = li.inflate(R.layout.extract_features_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(TrainingActivity.this);
        builder.setView(dialogView);

        TextView textView = (TextView) dialogView.findViewById(R.id.arffFileNameTextView);
        textView.setText(user.getTrainArffName());

        builder.setMessage(Helper.CONFIRM_MESSAGE)
                .setCancelable(true)
                .setPositiveButton("Back and Save", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int option) {
                        if(set != null) {
                            try {

                                // save user arff train file
                                WekaConnector.saveToARFF(getApplicationContext(),
                                        user.getTrainArffName(), set.getInstancesSet(), true);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        Intent i = new Intent(TrainingActivity.this, MainActivity.class);
                        startActivity(i);
                        TrainingActivity.this.finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int option) {

                    }
                })
                .show();
    }

    // show password
    public void showPassword(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        if(checked) {
            extract1.setInputType(InputType.TYPE_CLASS_TEXT);
            extract2.setInputType(InputType.TYPE_CLASS_TEXT);
            extract3.setInputType(InputType.TYPE_CLASS_TEXT);
            extract4.setInputType(InputType.TYPE_CLASS_TEXT);
            extract5.setInputType(InputType.TYPE_CLASS_TEXT);
        } else {
            extract1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            extract2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            extract3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            extract4.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            extract5.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

    }

}
