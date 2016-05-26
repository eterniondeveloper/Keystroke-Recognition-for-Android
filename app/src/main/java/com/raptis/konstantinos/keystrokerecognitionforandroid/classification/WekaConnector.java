package com.raptis.konstantinos.keystrokerecognitionforandroid.classification;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;

/**
 * Created by konstantinos on 18/5/2016.
 */
public class WekaConnector {

    // set classifier
    public static boolean test(Instances trainingSet, Instances testingSet) throws Exception {
        NaiveBayes nb = new NaiveBayes();
        nb.buildClassifier(trainingSet);

        double currentClassDouble = nb.classifyInstance(testingSet.firstInstance());

        Log.i(Helper.RESULT_LOG, "currentClassDouble: " + currentClassDouble);

        return true;
    }

    //----------------------------------------------------------------------------------------------
    //  EXTERNAL STORAGE
    //----------------------------------------------------------------------------------------------

    // delete ARFF file
    public static boolean deleteARFF(Context context, String arffName, boolean isPublic) {
        if(isExternalStorageWritable()) {
            File file = isPublic ? getARFFPublicStorageDir(arffName) : getARFFPrivateStorageDir(context, arffName);
            return file.delete();
        } else {
            Log.i(Helper.STORAGE_LOG, "External storage is not writable!");
            return false;
        }
    }

    // save to ARFF file
    public static boolean saveToARFF(Context context, String arffName, Instances instances, boolean isPublic) throws IOException {
        if(!instances.isEmpty() && isExternalStorageWritable()) {
            ArffSaver saver = new ArffSaver();
            saver.setInstances(instances);
            File file = isPublic ? getARFFPublicStorageDir(arffName) : getARFFPrivateStorageDir(context, arffName);
            saver.setFile(file);
            saver.setDestination(file);   // **not** necessary in 3.5.4 and later
            saver.writeBatch();
            return true;
        } else {
            return false;
        }
    }

    // load ARFF file
    public static Instances loadARFF(Context context, String arffName, boolean isPublic) throws IOException {
        if(isExternalStorageReadable()) {
            File file = isPublic ? getARFFPublicStorageDir(arffName) : getARFFPrivateStorageDir(context, arffName);
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

    public static File getARFFPrivateStorageDir(Context context, String arffName) {
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS), arffName);
        if (!file.mkdirs()) {
            Log.e(Helper.STORAGE_LOG, "Directory not created");
        }
        return file;
    }

    public static File getARFFPublicStorageDir(String arffName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), arffName);
        if (!file.mkdirs()) {
            Log.e(Helper.STORAGE_LOG, "Directory not created");
        }
        return file;
    }

}
