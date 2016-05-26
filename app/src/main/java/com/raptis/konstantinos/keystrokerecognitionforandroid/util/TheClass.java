package com.raptis.konstantinos.keystrokerecognitionforandroid.util;

/**
 * Created by konstantinos on 26/5/2016.
 */
public enum TheClass {

    NEGATIVE("NEGATIVE"),
    POSITIVE("POSITIVE");

    private String label;

    TheClass(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
