package com.raptis.konstantinos.keystrokerecognitionforandroid.classification;

import android.util.Log;

import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Helper;

import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;


/**
 * Created by konstantinos on 24/5/2016.
 */
public class WekaObject {

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
    private Instances instancesSet;

    public WekaObject(int numFDD, int numFUU, int numFUD, int numFD) {
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
        instancesSet = new Instances("Rel", fvWekaAttributes, 10);
        instancesSet.setClassIndex(instancesSet.numAttributes() - 1);
    }

    public void setInstances(double trFSE, double[] trFDD, double[] trFUU,
                             double[] trFUD, double[] trFD, double trFAHT, int trFER, String theClass) {
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
        if(theClass != null) {
            instance.setValue((Attribute) fvWekaAttributes.elementAt(index), theClass);
        }

        // add instance to training set
        instancesSet.add(instance);
    }

    // display training set
    public void displayInstancesSet() {
        Log.i(Helper.OUTPUT_LOG, instancesSet.toString());
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

    public Instances getInstancesSet() {
        return instancesSet;
    }

}

