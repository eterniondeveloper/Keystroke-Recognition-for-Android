package com.raptis.konstantinos.keystrokerecognitionforandroid.core;

/**
 * Created by konstantinos on 17/5/2016.
 */
public class KeyFactory {

    // total time (last release - first press)
    public static synchronized double getFSE(Buffer buffer) {
        return (double) ((buffer.getLast().getReleasedTime() - buffer.getFirst().getPressedTime()) / 1000000);
    }

    // time between key press to key press
    public static synchronized double[] getFDD(Buffer buffer) {
        double[] result = new double[buffer.getSize() - 1];
        long current = buffer.getFirst().getPressedTime();
        long next;
        for(int i = 1; i < buffer.getSize(); i++) {
            next = buffer.getElement(i).getPressedTime();
            result[i - 1] = (double) ((next - current) / 1000000);
            current = next;
        }
        return result;
    }

    // time between key release to key release
    public static synchronized double[] getFUU(Buffer buffer) {
        double[] result = new double[buffer.getSize() - 1];
        long current = buffer.getFirst().getReleasedTime();
        long next;
        for(int i = 1; i < buffer.getSize(); i++) {
            next = buffer.getElement(i).getReleasedTime();
            result[i - 1] = (double) ((next - current) / 1000000);
            current = next;
        }
        return result;
    }

    // time between key release and key press
    public static synchronized double[] getFUD(Buffer buffer) {
        double[] result = new double[buffer.getSize() - 1];
        long current = buffer.getFirst().getReleasedTime();
        long next;
        for(int i = 1; i < buffer.getSize(); i++) {
            next = buffer.getElement(i).getPressedTime();
            result[i - 1] = (double) ((next - current) / 1000000);
            current = next;
        }
        return result;
    }

    // time between key press and same key release (hold time)
    public static synchronized double[] getFD(Buffer buffer) {
        double[] result = new double[buffer.getSize()];
        for(int i = 0; i < buffer.getSize(); i++) {
            long currentPressedTime = buffer.getElement(i).getPressedTime();
            long currentReleasedTime = buffer.getElement(i).getReleasedTime();
            result[i] = (double) ((currentReleasedTime - currentPressedTime) / 1000000);
        }
        return result;
    }

    // average hold time
    public static synchronized double getFAHT(Buffer buffer) {
        double total = 0;
        for(int i = 0; i < buffer.getSize(); i++) {
            total += buffer.getElement(i).getHoldTime();
        }
        return total / buffer.getSize();
    }

}
