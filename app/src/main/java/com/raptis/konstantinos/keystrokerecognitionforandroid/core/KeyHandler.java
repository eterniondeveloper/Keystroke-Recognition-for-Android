package com.raptis.konstantinos.keystrokerecognitionforandroid.core;

import android.util.Log;

import com.raptis.konstantinos.keystrokerecognitionforandroid.db.dto.KeyObject;
import com.raptis.konstantinos.keystrokerecognitionforandroid.db.dto.User;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.DigraphType;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Helper;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Key;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Orientation;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Status;

import java.util.HashMap;

/**
 * Created by konstantinos on 17/4/2016.
 */
public class KeyHandler {

    // keys primary code mapping (Contain keys for which we want to maintain digraph type)
    public static final Key[]keys = {Key.ONE_BUTTON, Key.TWO_BUTTON, Key.THREE_BUTTON, Key.FOUR_BUTTON, Key.FIVE_BUTTON, Key.SIX_BUTTON, Key.SEVEN_BUTTON, Key.EIGHT_BUTTON, Key.NINE_BUTTON, Key.ZERO_BUTTON,
            Key.Q_BUTTON, Key.W_BUTTON, Key.E_BUTTON, Key.R_BUTTON, Key.T_BUTTON, Key.Y_BUTTON, Key.U_BUTTON, Key.I_BUTTON, Key.O_BUTTON, Key.P_BUTTON,
            Key.A_BUTTON, Key.S_BUTTON, Key.D_BUTTON, Key.F_BUTTON, Key.G_BUTTON, Key.H_BUTTON, Key.J_BUTTON, Key.K_BUTTON, Key.L_BUTTON, Key.SHARP_BUTTON,
            Key.CAPS_LOCK_BUTTON, Key.Z_BUTTON, Key.X_BUTTON, Key.C_BUTTON, Key.V_BUTTON, Key.B_BUTTON, Key.N_BUTTON, Key.M_BUTTON, Key.FULL_STOP_BUTTON, Key.QUESTION_MARK_BUTTON,
            Key.COMMA_BUTTON, Key.SLASH_BUTTON, Key.SPACE_BUTTON, Key.DELETE_BUTTON, Key.DONE_BUTTON,
            Key.AT_ANNOTATION_BUTTON, Key.EXCLAMATION_MARK_BUTTON, Key.COLON_BUTTON};

    // variables
    private int errorRateCounter = 0;
    public static HashMap<Integer, Key> keysMap;
    private static long timePressed, timeReleased;
    private static KeyObject currentKey;
    private Buffer buffer;
    private User user;

    // constructor
    public KeyHandler(int size, User user) {
        // create key map
        keysMap = new HashMap<>();
        for (Key key : keys) {
            keysMap.put(key.getPrimaryCode(), key);
        }
        // initialize buffer
        buffer = new Buffer(size);
        // initialize user
        this.user = user;
    }

    public boolean add(KeyObject keyObject) {

        /**
         * CHECK FOR INACTIVE KEYS
         */

        // Status inactive keys for now : space, delete, done
        if (keysMap.get(keyObject.getPrimaryCode()).getStatus() == Status.INACTIVE) {
            // check if delete pressed
            if (keyObject.getPrimaryCode() == Key.DELETE_BUTTON.getPrimaryCode()) {
                errorRateCounter++;
            }
            return false; // we don't want to buffer status = inactive keys
        }

        /**
         * CHECK IF TYPED KEY EXIST IN THE SAME SPOT THAT BUFFER INDEX CURRENTLY POINT
         */

        if (keyObject.getKeyChar() != user.getPassword().charAt(buffer.getIndex())) {
            return false;
        }

        /**
         * BUFFERING
         */

        buffer.add(keyObject);

        /**
         * DIGRAPH TYPE
         */

        // check digraph type (only if key exists in keysMap)
        if (keysMap.containsKey(keyObject.getPrimaryCode())) {
            DigraphType digraphType = getDigraphType(keyObject);
            keyObject.setDigraphType(digraphType);
        }
        return true;
    }

    // clear buffer
    public void clearBuffer() {
        buffer = new Buffer(buffer.getSize());
    }

    // get buffer
    public Buffer getBuffer() {
        return buffer;
    }

    // get error rate counter
    public int getErrorRateCounter() {
        return errorRateCounter;
    }

    // key pressed
    public void keyPressed(int primaryCode) {
        //Log.i(Helper.TEST_LOG, primaryCode + "");
        //--------------------------------------
        timePressed = System.nanoTime();
    }

    // key released
    public void keyReleased(int primaryCode) {
        //Log.i(Helper.TEST_LOG, primaryCode + "");
        //--------------------------------------
        timeReleased = System.nanoTime();
        currentKey = new KeyObject(primaryCode, keysMap.get(primaryCode).getKeyChar(), timePressed, timeReleased);
        // add key object to buffer
        boolean result = add(currentKey);
        ///////////////////////////
        // display only if key added to buffer otherwise -> NullPointerException will be thrown
        if(result) {
            Log.i(Helper.kEY_LOG, currentKey.toString());
            Log.i(Helper.TEST_LOG, buffer.display());
        }
    }

    //----------------------------------------------------------------------------------------------

    // Similar method with are areNeighbors but params are different
    // @Param : keyObj1 , keyObj2 are the KeyObjects of the two keys we want to check if those keys are adjacent
    // @return : true if they are adjacent, false if they aren't adjacent
    private boolean areAdjacent(KeyObject keyObj1, KeyObject keyObj2) {
        Key key1 = KeyHandler.keysMap.get(keyObj1.getPrimaryCode());
        Key key2 = KeyHandler.keysMap.get(keyObj2.getPrimaryCode());
        double distance = Math.sqrt(Math.pow(key1.getColumn() - key2.getColumn(), 2) + Math.pow(key1.getRow() - key2.getRow(), 2));
        return (distance < 2) ? true : false;
    }

    // @Param : keyObject is the key we want to examine its digraph type
    // @return : keyObject digraph type
    public DigraphType getDigraphType(KeyObject keyObject) {
        // digraph type will be NULL if current key is (space, delete or done) or if previous is null
        if((buffer.getPrevious() == null) ||
                keyObject.getPrimaryCode() == Key.SPACE_BUTTON.getPrimaryCode() ||
                keyObject.getPrimaryCode() == Key.DELETE_BUTTON.getPrimaryCode() ||
                keyObject.getPrimaryCode() == Key.DONE_BUTTON.getPrimaryCode()) {
            return DigraphType.NULL;
        }
        // digraph type will be SAME_KEY_DIGRAPH if previous key is the same with current (based on coordinations)
        int previousPrimaryCode =  buffer.getPrevious().getPrimaryCode();
        int currentPrimaryCode = keyObject.getPrimaryCode();
        if(keysMap.get(previousPrimaryCode).getColumn() == keysMap.get(currentPrimaryCode).getColumn() &&
                keysMap.get(previousPrimaryCode).getRow() == keysMap.get(currentPrimaryCode).getRow()) {
            return DigraphType.SAME_KEY_DIGRAPH;
        }
        // current with it's previous
        // boolean areAdjacent = areNeighbors(keyObject.getKeyChar(), keyObject.getPrevious().getKeyChar());
        boolean areAdjacent = areAdjacent(keyObject, buffer.getPrevious());
        Orientation orientation = getOrientation(keyObject);

        if(areAdjacent && (orientation == Orientation.HORIZONTAL)) {
            return DigraphType.ADJACENT_HORIZONTAL_DIGRAPH;
        } else if(areAdjacent && (orientation == Orientation.VERTICAL)) {
            return DigraphType.ADJACENT_VERTICAL_DIGRAPH;
        } else if((!areAdjacent) && (orientation == Orientation.HORIZONTAL)) {
            return DigraphType.NON_ADJACENT_HORIZONTAL_DIGRAPH;
        } else if((!areAdjacent) && (orientation == Orientation.VERTICAL)) {
            return DigraphType.NON_ADJACENT_VERTICAL_DIGRAPH;
        } else {
            return DigraphType.NULL;
        }
    }

    // @Param : keyObject with its previous
    // @return : orientation
    private Orientation getOrientation(KeyObject keyObject) {
        KeyObject previous = buffer.getPrevious();
        int currentRow = KeyHandler.keysMap.get(keyObject.getPrimaryCode()).getRow();
        int currentColumn = KeyHandler.keysMap.get(keyObject.getPrimaryCode()).getColumn();
        int previousRow = KeyHandler.keysMap.get(previous.getPrimaryCode()).getRow();
        int previousColumn = KeyHandler.keysMap.get(previous.getPrimaryCode()).getColumn();

        if(currentRow == previousRow) {
            return Orientation.HORIZONTAL;
        } else if(currentColumn == previousColumn) {
            return Orientation.VERTICAL;
        } else {
            return Orientation.NULL;
        }
    }

}
