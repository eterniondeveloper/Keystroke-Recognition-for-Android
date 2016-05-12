package com.raptis.konstantinos.keystrokerecognitionforandroid.components;

/**
 * Created by konstantinos on 11/5/2016.
 */


import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.raptis.konstantinos.keystrokerecognitionforandroid.MainActivity;

/**
 * Created by kwnstantinos on 27/3/2016.
 */
public class CustomEditText extends EditText implements View.OnKeyListener {

    // constructor
    public CustomEditText(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        //setOnKeyListener(this);
    }

    // constructor
    public CustomEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setOnKeyListener(this);
    }

    // constructor
    public CustomEditText(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setOnKeyListener(this);
    }

    //----------------------------------------------------------------------------------------------

    // on key up
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(event.getKeyCode() != 4) {   // 4 is back (not 100% sure)
            MainActivity.keyHandler.keyReleased(keyCode);
        }
        return super.onKeyUp(keyCode, event);
    }

    // on key down
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getKeyCode() != 4) { // 4 is back (not 100% sure)
            MainActivity.keyHandler.keyPressed(keyCode);
        }
        return super.onKeyDown(keyCode, event);
    }

    //----------------------------------------------------------------------------------------------

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        return super.onKeyPreIme(keyCode, event);
    }
}
