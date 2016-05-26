package com.raptis.konstantinos.keystrokerecognitionforandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.raptis.konstantinos.keystrokerecognitionforandroid.classification.WekaConnector;
import com.raptis.konstantinos.keystrokerecognitionforandroid.classification.WekaObject;
import com.raptis.konstantinos.keystrokerecognitionforandroid.components.CustomEditText;
import com.raptis.konstantinos.keystrokerecognitionforandroid.core.Buffer;
import com.raptis.konstantinos.keystrokerecognitionforandroid.core.KeyFactory;
import com.raptis.konstantinos.keystrokerecognitionforandroid.db.DBConnector;
import com.raptis.konstantinos.keystrokerecognitionforandroid.db.dto.User;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Helper;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.TheClass;

import java.io.IOException;
import java.util.ArrayList;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class MainActivity extends AppCompatActivity {

    // variables
    private ArrayList<User> users;
    private DBConnector connector;
    private CustomEditText passwordEditText;

    // on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connector = new DBConnector(getApplicationContext(), null, null, DBConnector.DATABASE_VERSION);
        users = connector.getAllUsers();

        TextView usernameTextView = (TextView) findViewById(R.id.usernameTextView);
        usernameTextView.setText(users.get(0).getUsername());

        passwordEditText = (CustomEditText) findViewById(R.id.testingPasswordEdiText);
        passwordEditText.init(users.get(0).getPassword());
    }

    // train
    public void train(View view) {
        Intent i = new Intent(MainActivity.this, TrainingActivity.class);
        i.putExtra("user", users.get(0));
        startActivity(i);
        MainActivity.this.finish();
    }

    // delete
    public void delete(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage(Helper.DELETE_USER_MESSAGE)
                .setCancelable(true)
                .setPositiveButton("Yes", dialogListener)
                .setNegativeButton("No", dialogListener)
                .show();
    }

    DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialogInterface, int option) {
            switch (option) {
                case DialogInterface.BUTTON_POSITIVE:
                    User user = users.get(0);
                    connector.deleteUser(user.getUsername());
                    WekaConnector.deleteARFF(getApplicationContext(), user.getTrainArffName(), true);
                    Intent i = new Intent(MainActivity.this, CreateUserActivity.class);
                    startActivity(i);
                    MainActivity.this.finish();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    // test
    public void test(View view) {
        if(passwordEditText.getText().toString().equals(users.get(0).getPassword())) {
            Buffer b = passwordEditText.getKeyHandler().getBuffer();
            double tempFSE = KeyFactory.getFSE(b);
            double[] tempFDD = KeyFactory.getFDD(b);
            double[] tempFUU = KeyFactory.getFUU(b);
            double[] tempFUD = KeyFactory.getFUD(b);
            double[] tempFD = KeyFactory.getFD(b);
            double tempFAHT = KeyFactory.getFAHT(b);

            WekaObject testingObj = new WekaObject(tempFDD.length, tempFUU.length,
                    tempFUD.length, tempFD.length, users.get(0).getPassword());

            testingObj.addInstance(tempFSE, tempFDD, tempFUU, tempFUD, tempFD, tempFAHT,
                    passwordEditText.getKeyHandler().getErrorRateCounter(), null);

            try {
                Instances trainInstances = WekaConnector.loadARFF(getApplicationContext(), users.get(0).getTrainArffName(), true);

                if (trainInstances.classIndex() == -1) {
                    trainInstances.setClassIndex(trainInstances.numAttributes() - 1);
                }

                NaiveBayes nb = new NaiveBayes();
                nb.buildClassifier(trainInstances);

                double currentClassDouble = nb.classifyInstance(testingObj.getInstancesSet().get(0));
                String currentClassString;

                switch ((int) currentClassDouble) {
                    case 0:
                        currentClassString = TheClass.POSITIVE.getLabel();
                        Toast.makeText(MainActivity.this, currentClassString, Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        currentClassString = TheClass.NEGATIVE.getLabel();
                        Toast.makeText(MainActivity.this, currentClassString, Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "UNKNOWN", Toast.LENGTH_LONG).show();
                }

                passwordEditText.init(users.get(0).getPassword());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(MainActivity.this, "Not Valid Password", Toast.LENGTH_LONG).show();
        }
    }

}
