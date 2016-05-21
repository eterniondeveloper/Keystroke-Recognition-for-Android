package com.raptis.konstantinos.keystrokerecognitionforandroid.components;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

import com.raptis.konstantinos.keystrokerecognitionforandroid.core.KeyHandler;
import com.raptis.konstantinos.keystrokerecognitionforandroid.db.dto.User;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Helper;
import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Key;

/**
 * Created by konstantinos on 20/5/2016.
 */

/**
 * Created by kwnstantinos on 27/3/2016.
 */
public class CustomPasswordEditText extends EditText {

    private KeyHandler keyHandler;
    private User user;
    private boolean ready = false;

    // init
    public void init(User user) {
        this.user = user;
        keyHandler = new KeyHandler(user.getPassword().length(), user);
    }

    // constructor
    public CustomPasswordEditText(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    // constructor
    public CustomPasswordEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    // constructor
    public CustomPasswordEditText(Context context, AttributeSet attributeSet, int defStyle) {
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

    public User getUser() {
        return user;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public boolean isReady() {
        return ready;
    }

}
