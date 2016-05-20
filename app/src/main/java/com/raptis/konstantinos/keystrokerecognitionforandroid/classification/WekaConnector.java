package com.raptis.konstantinos.keystrokerecognitionforandroid.classification;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;

/**
 * Created by konstantinos on 18/5/2016.
 */
public class WekaConnector {

    /**
     * FEATURES
     */
    public static final String FSE = "FSE";
    public static final String FDD = "FDD";
    public static final String FUU = "FUU";
    public static final String FUD = "FUD";
    public static final String FD = "FD";
    public static final String FAHT = "FAHT";

    public static final String FER = "FER";

    public static final String THE_CLASS = "THE_CLASS";

    public static final String POSITIVE = "POSITIVE";
    public static final String NEGATIVE = "NEGATIVE";

    public final int NUMBER_OF_FSE_FEATURES = 1;
    public final int NUMBER_OF_FDD_FEATURES;
    public final int NUMBER_OF_FUU_FEATURES;
    public final int NUMBER_OF_FUD_FEATURES;
    public final int NUMBER_OF_FD_FEATURES;
    public final int NUMBER_OF_FAHT_FEATURES = 1;
    public final int NUMBER_OF_FER_FEATURES = 1;
    public final int NUMBER_OF_THE_CLASS_FEATURES = 1;
    public final int NUMBER_OF_FEATURES;

    private Attribute attrFSE;
    private ArrayList<Attribute> attrFDDList;
    private ArrayList<Attribute> attrFUUList;
    private ArrayList<Attribute> attrFUDList;
    private ArrayList<Attribute> attrFDList;
    private Attribute attrFAHT;
    private Attribute attrFER;
    private Attribute attrClass;

    private FastVector fvWekaAttributes;
    private Instances trainingSet;

    public WekaConnector(int numFDD, int numFUU, int numFUD, int numFD) {
        NUMBER_OF_FDD_FEATURES = numFDD;
        NUMBER_OF_FUU_FEATURES = numFUU;
        NUMBER_OF_FUD_FEATURES = numFUD;
        NUMBER_OF_FD_FEATURES = numFD;
        NUMBER_OF_FEATURES = NUMBER_OF_FSE_FEATURES + numFDD + numFUU + numFUD + numFD + NUMBER_OF_FAHT_FEATURES
            + NUMBER_OF_FER_FEATURES + NUMBER_OF_THE_CLASS_FEATURES;

        attrFDDList = new ArrayList<>(NUMBER_OF_FDD_FEATURES);
        attrFUUList = new ArrayList<>(NUMBER_OF_FUU_FEATURES);
        attrFUDList = new ArrayList<>(NUMBER_OF_FUD_FEATURES);
        attrFDList = new ArrayList<>(NUMBER_OF_FD_FEATURES);

        /*
        INIT ATTRIBUTES
         */

        // FSE
        attrFSE = new Attribute(FSE);

        // FDD
        Attribute attributeFDD;
        for(int i = 0; i < NUMBER_OF_FDD_FEATURES; i++) {
            attributeFDD = new Attribute(FDD + i);
            attrFDDList.add(attributeFDD);
        }

        // FUU
        Attribute attributeFUU;
        for(int i = 0; i < NUMBER_OF_FUU_FEATURES; i++) {
            attributeFUU = new Attribute(FUU + i);
            attrFUUList.add(attributeFUU);
        }

        // FUD
        Attribute attributeFUD;
        for(int i = 0; i < NUMBER_OF_FUD_FEATURES; i++) {
            attributeFUD = new Attribute(FUD + i);
            attrFUDList.add(attributeFUD);
        }

        // FD
        Attribute attributeFD;
        for(int i = 0; i < NUMBER_OF_FD_FEATURES; i++) {
            attributeFD = new Attribute(FD + i);
            attrFDList.add(attributeFD);
        }

        // FAHT
        attrFAHT = new Attribute(FAHT);

        // FER
        attrFER = new Attribute(FER);

        // Declare the class attribute along with its values
        FastVector fvTheClass = new FastVector(2);
        fvTheClass.addElement(POSITIVE);
        fvTheClass.addElement(NEGATIVE);
        attrClass = new Attribute(THE_CLASS, fvTheClass);

        // Declare the feature vector
        fvWekaAttributes = new FastVector(NUMBER_OF_FEATURES);

        // add attribute FSE to feature vector
        fvWekaAttributes.addElement(attrFSE);

        // add attributes FDD to feature vector
        for(Attribute attribute : attrFDDList) {
            fvWekaAttributes.addElement(attribute);
        }

        // add attributes FUU to feature vector
        for(Attribute attribute : attrFUUList) {
            fvWekaAttributes.addElement(attribute);
        }

        // add attributes FUD to feature vector
        for(Attribute attribute : attrFUDList) {
            fvWekaAttributes.addElement(attribute);
        }

        // add attributes FD to feature vector
        for(Attribute attribute : attrFDList) {
            fvWekaAttributes.addElement(attribute);
        }

        // add attribute FAHT to feature vector
        fvWekaAttributes.addElement(attrFAHT);

        // add attribute FER to feature vector
        fvWekaAttributes.addElement(attrFER);

        // add attribute THE_CLASS to feature vector
        fvWekaAttributes.addElement(attrClass);

        /*
        INIT TRAINING SET
         */
        trainingSet = new Instances("Rel", fvWekaAttributes, 10);
    }

    public void train(double trFSE, double[] trFDD, double[] trFUU,
                      double[] trFUD, double[] trFD, double trFAHT, int trFER) {
        // fill training set
        Instance instance = new DenseInstance(NUMBER_OF_FEATURES);
        int index = 0;

        // set FSE attribute
        instance.setValue((Attribute) fvWekaAttributes.elementAt(index++), trFSE);

        // set FDD attributes
        for(int i = 0; i < trFDD.length; i++) {
            instance.setValue((Attribute) fvWekaAttributes.elementAt(index++), trFDD[i]);
        }

        // set FUU attributes
        for(int i = 0; i < trFUU.length; i++) {
            instance.setValue((Attribute) fvWekaAttributes.elementAt(index++), trFUU[i]);
        }

        // set FUD attributes
        for(int i = 0; i < trFUD.length; i++) {
            instance.setValue((Attribute) fvWekaAttributes.elementAt(index++), trFUD[i]);
        }

        // set FD attributes
        for(int i = 0; i < trFD.length; i++) {
            instance.setValue((Attribute) fvWekaAttributes.elementAt(index++), trFD[i]);
        }

        // set FAHT attribute
        instance.setValue((Attribute) fvWekaAttributes.elementAt(index++), trFAHT);

        // set FER attribute
        instance.setValue((Attribute) fvWekaAttributes.elementAt(index++), trFER);

        // set CLASS attribute
        instance.setValue((Attribute) fvWekaAttributes.elementAt(index), POSITIVE);

        // add instance to training set
        trainingSet.add(instance);
    }

    // save to ARFF
    public boolean saveToARFF(Context context) throws IOException {
        if(!trainingSet.isEmpty() && isExternalStorageWritable()) {
            ArffSaver saver = new ArffSaver();
            saver.setInstances(trainingSet);
            File file = getARFFStorageDir(context, "test_train.arff");
            saver.setFile(file);
            saver.setDestination(file);   // **not** necessary in 3.5.4 and later
            saver.writeBatch();
            return true;
        } else {
            return false;
        }
    }

    // load ARFF
    public static Instances loadARFF(Context context) throws IOException {
        if(isExternalStorageReadable()) {
            File file = getARFFStorageDir(context, "test_train.arff");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
            Instances data = arff.getData();
            data.setClassIndex(data.numAttributes() - 1);
            return data;
        } else {
            Log.i(Helper.STORAGE_LOG, "External storage is not readable!");
            return null;
        }
    }

    // delete ARFF file
    public boolean deleteARFF(Context context) {
        if(isExternalStorageWritable()) {
            File file = getARFFStorageDir(context, "test_train.arff");
            return file.delete();
        } else {
            Log.i(Helper.STORAGE_LOG, "External storage is not writable!");
            return false;
        }
    }

    // display training set
    public void displayTrainingSet() {
        Log.i(Helper.OUTPUT_LOG, trainingSet.toString());
    }

    // set classifier
    public static boolean test(Instances trainingSet, Instances testingSet) throws Exception {
        // Create a naïve bayes classifier
        Classifier cModel = (Classifier) new NaiveBayes();
        cModel.buildClassifier(trainingSet);

        // Test the model
        Evaluation eTest = new Evaluation(trainingSet);
        eTest.evaluateModel(cModel, testingSet);
        return true;
    }

    // getters
    public int getNUMBER_OF_FSE_FEATURES() {
        return NUMBER_OF_FSE_FEATURES;
    }

    public int getNUMBER_OF_FDD_FEATURES() {
        return NUMBER_OF_FDD_FEATURES;
    }

    public int getNUMBER_OF_FUU_FEATURES() {
        return NUMBER_OF_FUU_FEATURES;
    }

    public int getNUMBER_OF_FUD_FEATURES() {
        return NUMBER_OF_FUD_FEATURES;
    }

    public int getNUMBER_OF_FD_FEATURES() {
        return NUMBER_OF_FD_FEATURES;
    }

    public int getNUMBER_OF_FAHT_FEATURES() {
        return NUMBER_OF_FAHT_FEATURES;
    }

    //----------------------------------------------------------------------------------------------
    //  EXTERNAL STORAGE
    //----------------------------------------------------------------------------------------------

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static File getARFFStorageDir(Context context, String arffName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS), arffName);
        if (!file.mkdirs()) {
            Log.e(Helper.STORAGE_LOG, "Directory not created");
        }
        return file;
    }

}
