package com.raptis.konstantinos.keystrokerecognitionforandroid.db;

/**
 * Created by konstantinos on 13/5/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.raptis.konstantinos.keystrokerecognitionforandroid.core.Buffer;
import com.raptis.konstantinos.keystrokerecognitionforandroid.db.dto.KeyObject;
import com.raptis.konstantinos.keystrokerecognitionforandroid.db.dto.User;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.DigraphType;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Helper;

import java.util.ArrayList;


public class DBConnector extends SQLiteOpenHelper {

    // Database version
    public static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "keystroke_recognition.db";

    // Table names
    private static final String USER_TABLE = "my_user";
    private static final String TRAINING_SESSION_TABLE = "my_training_session";
    private static final String KEY_TABLE = "my_key";

    // user
    private static final String USER_EMAIL = "user_e_mail";
    private static final String USER_PASSWORD = "user_password";
    private static final String USER_FIRST_NAME = "user_first_name";
    private static final String USER_LAST_NAME = "user_last_name";
    private static final String USER_ARFF_FILE_NAME = "user_arff_file_name";    // training set file name (.arff)

    // training session
    private static final String TRAINING_SESSION_ID = "training_session_id";

    // key
    private static final String KEY_ID = "key_id";
    private static final String KEY_PRIMARY_CODE = "key_primary_code";
    private static final String KEY_CHAR = "key_char";
        private static final String KEY_PRESSED_TIME = "key_pressed_time";
    private static final String KEY_RELEASED_TIME = "key_released_time";
    private static final String KEY_DIGRAPH_TYPE = "key_digraph_type";

    // create user table
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + USER_TABLE + " ("
            + USER_EMAIL + " TEXT PRIMARY KEY UNIQUE not null,"
            + USER_PASSWORD + " TEXT not null,"
            + USER_FIRST_NAME + " TEXT not null,"
            + USER_LAST_NAME + " TEXT not null,"
            + USER_ARFF_FILE_NAME + " TEXT not null)";

    // create training session table
    private static final String CREATE_TABLE_TRAINING_SESSION = "CREATE TABLE " + TRAINING_SESSION_TABLE + " ("
            + TRAINING_SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + USER_EMAIL + " TEXT not null,"
            + " FOREIGN KEY (" + USER_EMAIL + ") REFERENCES " + USER_TABLE + "(" + USER_EMAIL + ")"
            + " ON DELETE RESTRICT ON UPDATE RESTRICT)";

    // create key table
    private static final String CREATE_TABLE_KEY = "CREATE TABLE " + KEY_TABLE + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TRAINING_SESSION_ID + " INTEGER not null,"
            + KEY_PRIMARY_CODE + " INTEGER not null,"
            + KEY_CHAR + " TEXT not null,"
            + KEY_PRESSED_TIME + " LONG not null,"
            + KEY_RELEASED_TIME + " LONG not null,"
            + KEY_DIGRAPH_TYPE + " TEXT not null,"
            + " FOREIGN KEY (" + TRAINING_SESSION_ID + ") REFERENCES " + TRAINING_SESSION_TABLE + "(" + TRAINING_SESSION_ID + ")"
            + " ON DELETE RESTRICT ON UPDATE RESTRICT)";

    // Constructor
    public DBConnector(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
    }

    // On create
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Creating required tables
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_TRAINING_SESSION);
        db.execSQL(CREATE_TABLE_KEY);
    }

    // On upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // On upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_TRAINING_SESSION);
        db.execSQL("DROP TABLE IF EXISTS " + KEY_TABLE);

        // Create new tables
        onCreate(db);
    }

    //----------------------------------------------------------------------------------------------
    // User
    //----------------------------------------------------------------------------------------------

    // add user
    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_FIRST_NAME, user.getFirstName());
        values.put(USER_LAST_NAME, user.getLastName());
        values.put(USER_ARFF_FILE_NAME, user.getArffName());

        // Insert row
        return db.insert(USER_TABLE, null, values) != -1 ? true : false;
    }

    // get user
    public User getUser(String userEmail, String userPassword) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + USER_TABLE + " WHERE "
                + USER_EMAIL + " = " + "'" + userEmail + "'";
        User user = null;
        String tempPassword = "";
        boolean result = false;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {

            String tempEmail = cursor.getString(cursor.getColumnIndex(USER_EMAIL));
            tempPassword = cursor.getString(cursor.getColumnIndex(USER_PASSWORD));
            String tempFirstName = cursor.getString(cursor.getColumnIndex(USER_FIRST_NAME));
            String tempLastName = cursor.getString(cursor.getColumnIndex(USER_LAST_NAME));
            String tempArffName = cursor.getString(cursor.getColumnIndex(USER_ARFF_FILE_NAME));
            user = new User();
            user.setEmail(tempEmail);
            user.setFirstName(tempFirstName);
            user.setLastName(tempLastName);
            user.setArffName(tempArffName);

            // check if provided password match with created password came from database
            if (userPassword.equals(tempPassword)) {
                result = true;
                user.setPassword(tempPassword);
            }
        }
        return result ? user : null;
    }

    // get user
    public User getUser(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + USER_TABLE + " WHERE "
                + USER_EMAIL + " = " + "'" + userEmail + "'";
        User user = null;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            String tempEmail = cursor.getString(cursor.getColumnIndex(USER_EMAIL));
            String tempPassword = cursor.getString(cursor.getColumnIndex(USER_PASSWORD));
            String tempFirstName = cursor.getString(cursor.getColumnIndex(USER_FIRST_NAME));
            String tempLastName = cursor.getString(cursor.getColumnIndex(USER_LAST_NAME));
            String tempArffName = cursor.getString(cursor.getColumnIndex(USER_ARFF_FILE_NAME));
            user = new User();
            user.setEmail(tempEmail);
            user.setPassword(tempPassword);
            user.setFirstName(tempFirstName);
            user.setLastName(tempLastName);
            user.setArffName(tempArffName);
        }
        return user;
    }

    //----------------------------------------------------------------------------------------------
    // Training Session
    //----------------------------------------------------------------------------------------------

    public boolean addTrainingSession(Buffer buffer, String userEmail) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_EMAIL, userEmail);

        long trainingSessionId = db.insert(TRAINING_SESSION_TABLE, null, values);
        boolean result = false;

        if(trainingSessionId != - 1) {
            for(int i = 0; i < buffer.getSize(); i++) {
                KeyObject tempKeyObject = buffer.getElement(i);
                result = addKey(tempKeyObject, (int) trainingSessionId);
            }
        } else {
            return false;
        }
        return result;
    }

    public ArrayList<Buffer> getTrainingSessions(String userEmail, String userPassword) {
        if(getUser(userEmail, userPassword) != null) {
            SQLiteDatabase db = this.getReadableDatabase();

            String selectQuery = "SELECT " + TRAINING_SESSION_ID + " FROM " + TRAINING_SESSION_TABLE + " WHERE "
                    + USER_EMAIL + " = " + "'" + userEmail + "'";

            Cursor cursor = db.rawQuery(selectQuery, null);
            ArrayList<Integer> trainingSessionIds = new ArrayList<>();
            ArrayList<Buffer> buffers = new ArrayList<>();

            // Looping through all rows and adding to list
            if(cursor.moveToFirst()) {
                do {
                    int tempTrainingSessionId = cursor.getInt(cursor.getColumnIndex(TRAINING_SESSION_ID));
                    trainingSessionIds.add(tempTrainingSessionId);
                } while (cursor.moveToNext());

                if(!trainingSessionIds.isEmpty()) {
                    for(Integer trainingSession : trainingSessionIds) {
                        ArrayList<KeyObject> keys = getKeys(trainingSession);
                        if(keys != null) {
                            Buffer buffer = new Buffer(keys.size());
                            for (KeyObject keyObject : keys) {
                                buffer.add(keyObject);
                            }
                            buffers.add(buffer);
                        } else {
                            Log.i(Helper.DB_LOG, "Keys ArrayList is null!");
                            return null;
                        }
                    }
                } else {
                    Log.i(Helper.DB_LOG, "Training Session Ids ArrayList is empty!");
                    return null;
                }
            }
            cursor.close();
            db.close();
            return buffers;
        } else {
            Log.i(Helper.DB_LOG, "Added user user email and password return null!");
            return null;
        }
    }

    //----------------------------------------------------------------------------------------------
    // Key
    //----------------------------------------------------------------------------------------------

    // add key
    private boolean addKey(KeyObject keyObject, int trainingSessionId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRIMARY_CODE, keyObject.getPrimaryCode());
        values.put(TRAINING_SESSION_ID, trainingSessionId);
        values.put(KEY_CHAR, String.format("%c", keyObject.getKeyChar()));
        values.put(KEY_PRESSED_TIME, keyObject.getPressedTime());
        values.put(KEY_RELEASED_TIME, keyObject.getReleasedTime());
        values.put(KEY_DIGRAPH_TYPE, keyObject.getDigraphType().toString().toLowerCase());

        // Insert row
        return db.insert(KEY_TABLE, null, values) != -1 ? true : false;
    }

    // get keys
    private ArrayList<KeyObject> getKeys(int trainingSessionId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + KEY_TABLE + " WHERE "
                + TRAINING_SESSION_ID + " = " + "'" + trainingSessionId + "'";
        ArrayList<KeyObject> keys = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if(cursor.moveToFirst()) {
            do {
                int tempKeyId = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                int tempPrimaryCode = cursor.getInt(cursor.getColumnIndex(KEY_PRIMARY_CODE));
                String tempTrainingSessionId = cursor.getString(cursor.getColumnIndex(TRAINING_SESSION_ID));
                String tempKeyChar = cursor.getString(cursor.getColumnIndex(KEY_CHAR));
                long tempPressedTime = cursor.getLong(cursor.getColumnIndex(KEY_PRESSED_TIME));
                long tempReleasedTime = cursor.getLong(cursor.getColumnIndex(KEY_RELEASED_TIME));
                String tempDigraphType = cursor.getString(cursor.getColumnIndex(KEY_DIGRAPH_TYPE));

                KeyObject keyObject = new KeyObject();
                keyObject.setPrimaryCode(tempPrimaryCode);
                keyObject.setKeyChar(tempKeyChar.charAt(0));
                keyObject.setPressedTime(tempPressedTime);
                keyObject.setReleasedTime(tempReleasedTime);

                switch (tempDigraphType) {
                    case "null":
                        keyObject.setDigraphType(DigraphType.NULL);
                        break;
                    case "same_key_digraph":
                        keyObject.setDigraphType(DigraphType.SAME_KEY_DIGRAPH);
                        break;
                    case "adjacent_horizontal_digraph":
                        keyObject.setDigraphType(DigraphType.ADJACENT_HORIZONTAL_DIGRAPH);
                        break;
                    case "adjacent_vertical_digraph":
                        keyObject.setDigraphType(DigraphType.ADJACENT_VERTICAL_DIGRAPH);
                        break;
                    case "non_adjacent_horizontal_digraph":
                        keyObject.setDigraphType(DigraphType.NON_ADJACENT_HORIZONTAL_DIGRAPH);
                        break;
                    case "non_adjacent_vertical_digraph":
                        keyObject.setDigraphType(DigraphType.NON_ADJACENT_VERTICAL_DIGRAPH);
                        break;
                }
                keys.add(keyObject);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return keys;
    }

}

