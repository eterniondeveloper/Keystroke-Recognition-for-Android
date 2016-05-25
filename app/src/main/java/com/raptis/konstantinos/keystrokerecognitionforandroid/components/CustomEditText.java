package com.raptis.konstantinos.keystrokerecognitionforandroid.components;

/**
 * Created by konstantinos on 11/5/2016.
 */


import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

import com.raptis.konstantinos.keystrokerecognitionforandroid.core.KeyHandler;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Helper;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Key;

/**
 * Created by kwnstantinos on 27/3/2016.
 */
public class CustomEditText extends EditText {

    private KeyHandler keyHandler;
    private boolean ready = false;
    private String password;

    // init
    public void init(String password) {
        this.password = password;
        keyHandler = new KeyHandler(password);
    }

    // constructor
    public CustomEditText(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    // constructor
    public CustomEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    // constructor
    public CustomEditText(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    //----------------------------------------------------------------------------------------------

    // on key up
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.i(Helper.KEY_PRESSED, "onKeyUp: keyCode" + keyCode + " ; event.getKeyCode(): " + event.getKeyCode());
        if(event.getKeyCode() != Key.DONE_BUTTON.getPrimaryCode() &&
                event.getKeyCode() != 4) {
            keyHandler.keyReleased(keyCode);
        }

        // check if typed password match user password
        // if yes turn line indicator green
        if(this.getText().toString().equals(password)) {
            this.getBackground().mutate().setColorFilter(Color.parseColor("#16A085"), PorterDuff.Mode.SRC_ATOP);
            ready = true;
        } else if(this.getText().toString().equals("")) {
            this.getBackground().mutate().setColorFilter(Color.parseColor("#757575"), PorterDuff.Mode.SRC_ATOP);
            ready = false;

        } else {
            this.getBackground().mutate().setColorFilter(Color.parseColor("#E74C3C"), PorterDuff.Mode.SRC_ATOP);
            ready = false;
        }
        return super.onKeyUp(keyCode, event);
    }

    // on key down
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i(Helper.KEY_PRESSED, "onKeyDown: " + keyCode + " ; event.getKeyCode(): " + event.getKeyCode());
        if(event.getKeyCode() != Key.DONE_BUTTON.getPrimaryCode() &&
                event.getKeyCode() != 4) {
            keyHandler.keyPressed(keyCode);
        }
        return super.onKeyDown(keyCode, event);
    }

    //----------------------------------------------------------------------------------------------

    // return true if editText is empty or false if not
    public boolean isEmpty() {
        return this.getText().toString().trim().length() == 0;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public boolean isReady() {
        return ready;
    }

}
