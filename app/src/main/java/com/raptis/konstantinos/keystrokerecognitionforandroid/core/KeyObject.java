package com.raptis.konstantinos.keystrokerecognitionforandroid.core;

import com.raptis.konstantinos.keystrokerecognitionforandroid.util.DigraphType;

/**
 * Created by konstantinos on 17/4/2016.
 */

public class KeyObject {

    // field variables
    private int primaryCode;
    private Character keyChar;
    private long pressedTime;
    private long releasedTime;
    private DigraphType digraphType;

    // constructor
    public KeyObject() {

    }

    // constructor
    public KeyObject(int primaryCode, Character keyChar, long pressedTime, long releasedTime) {
        this.primaryCode = primaryCode;
        this.keyChar = keyChar.toString().toLowerCase().charAt(0);
        this.pressedTime = pressedTime;
        this.releasedTime = releasedTime;
    }

    // setters

    public void setPrimaryCode(int primaryCode) {
        this.primaryCode = primaryCode;
    }

    public void setKeyChar(Character keyChar) {
        this.keyChar = keyChar;
    }

    public void setPressedTime(long pressedTime) {
        this.pressedTime = pressedTime;
    }

    public void setReleasedTime(long releasedTime) {
        this.releasedTime = releasedTime;
    }

    public void setDigraphType(DigraphType digraphType) {
        this.digraphType = digraphType;
    }


    // getters


    public int getPrimaryCode() {
        return primaryCode;
    }

    public Character getKeyChar() {
        return keyChar;
    }

    public long getPressedTime() {
        return pressedTime;
    }

    public long getReleasedTime() {
        return releasedTime;
    }

    public DigraphType getDigraphType() {
        return digraphType;
    }

    // get key hold time (ms)
    public double getHoldTime() {
        return (releasedTime - pressedTime) / 1000000;
    }

    // to String
    @Override
    public String toString() {
        return String.format("Key: %2c; Pressed Time: %7.0f ms; Release Time: %7.0f ms; Hold Time: %7.0f ms; Digraph Type: %-1s",
                keyChar, (double) (pressedTime / 1000000), (double) (releasedTime / 1000000), getHoldTime(), digraphType.toString());
    }

}
