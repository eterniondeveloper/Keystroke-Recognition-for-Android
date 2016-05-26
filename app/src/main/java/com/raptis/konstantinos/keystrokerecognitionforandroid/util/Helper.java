package com.raptis.konstantinos.keystrokerecognitionforandroid.util;

import android.widget.EditText;

/**
 * Created by konstantinos on 16/4/2016.
 */
public class Helper {

    // logcat ids
    public static final String TEST_LOG = "testlog";
    public static final String CM_LOG = "cmlog";
    public static final String kEY_ID_LOG = "keyidlog";
    public static final String KEY_PRESSED = "press";
    public static final String kEY_LOG = "keylog";
    public static final String USER_LOG = "userlog";
    public static final String OUTPUT_LOG = "outputlog";
    public static final String DB_LOG = "dblog";
    public static final String STORAGE_LOG = "storagelog";
    public static final String RESULT_LOG = "resultlog";

    // useful messages
    public static final String NOT_ALL_FIELDS_FILLED = "You have to fill all fields before SUBMIT";
    public static final String CONFIRM_MESSAGE = "Are you sure you want to quit extracting procedure?";
    public static final String DELETE_USER_MESSAGE = "Are you sure you want to delete current user?";
    public static final String NOT_ALL_FIELDS_FILLED_CORRECTLY = "Password isn't correct in every field!!!";
    public static final String INVALID_PASSWORD = "Invalid Password!!!";
    public static final String INVALID_USERNAME = "Invalid Username!!!";

    public static final int TRAINING_COUNT = 10;

    public static boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }
}
