package com.raptis.konstantinos.keystrokerecognitionforandroid.util;

/**
 * Created by konstantinos on 17/4/2016.
 */
public enum Key {

    // Line 1
    ONE_BUTTON('1', 49, 0, 0, Status.ACTIVE), TWO_BUTTON('2', 50, 0, 1, Status.ACTIVE), THREE_BUTTON('3', 51, 0, 2, Status.ACTIVE), FOUR_BUTTON('4', 52, 0, 3, Status.ACTIVE),
    FIVE_BUTTON('5', 53, 0, 4, Status.ACTIVE), SIX_BUTTON('6', 54, 0, 5, Status.ACTIVE), SEVEN_BUTTON('7', 55, 0, 6, Status.ACTIVE), EIGHT_BUTTON('8', 56, 0, 7, Status.ACTIVE),
    NINE_BUTTON('9', 57, 0, 8, Status.ACTIVE), ZERO_BUTTON('0', 48, 0, 9, Status.ACTIVE),

    // Line 2
    Q_BUTTON('q', 113, 1, 0, Status.ACTIVE), W_BUTTON('w', 119, 1, 1, Status.ACTIVE), E_BUTTON('e', 101, 1, 2, Status.ACTIVE), R_BUTTON('r', 114, 1, 3, Status.ACTIVE),
    T_BUTTON('t', 116, 1, 4, Status.ACTIVE), Y_BUTTON('y', 121, 1, 5, Status.ACTIVE), U_BUTTON('u', 117, 1, 6, Status.ACTIVE), I_BUTTON('i', 105, 1, 7, Status.ACTIVE),
    O_BUTTON('o', 111, 1, 8, Status.ACTIVE), P_BUTTON('p', 112, 1, 9, Status.ACTIVE),

    // Line 3
    A_BUTTON('a', 97, 2, 0, Status.ACTIVE), S_BUTTON('s', 115, 2, 1, Status.ACTIVE), D_BUTTON('d', 100, 2, 2, Status.ACTIVE), F_BUTTON('f', 102, 2, 3, Status.ACTIVE),
    G_BUTTON('g', 103,2 , 4, Status.ACTIVE), H_BUTTON('h', 104, 2, 5, Status.ACTIVE), J_BUTTON('j', 106, 2, 6, Status.ACTIVE), K_BUTTON('k', 107, 2, 7, Status.ACTIVE),
    L_BUTTON('l', 108, 2, 8, Status.ACTIVE), SHARP_BUTTON('#', 35, 2, 9, Status.ACTIVE),

    // Line 4
    CAPS_LOCK_BUTTON('↑', -1, 3, 0, Status.INACTIVE), Z_BUTTON('z', 122, 3, 1, Status.ACTIVE), X_BUTTON('x', 120, 3, 2, Status.ACTIVE), C_BUTTON('c', 99, 3, 3, Status.ACTIVE),
    V_BUTTON('v', 118, 3, 4, Status.ACTIVE), B_BUTTON('b', 98, 3, 5, Status.ACTIVE), N_BUTTON('n', 110, 3, 6, Status.ACTIVE), M_BUTTON('m', 109, 3, 7, Status.ACTIVE),
    FULL_STOP_BUTTON('.', 46, 3, 8, Status.ACTIVE), QUESTION_MARK_BUTTON('?', 63, 3, 9, Status.ACTIVE),

    // Line 5
    COMMA_BUTTON(',', 44, 4, 0, Status.ACTIVE), SLASH_BUTTON('/', 47, 4, 1, Status.ACTIVE), SPACE_BUTTON(' ', 32, 4, 2, Status.INACTIVE), DELETE_BUTTON('←', -5, 4, 3, Status.INACTIVE),
    DONE_BUTTON('→', -4, 4, 4, Status.INACTIVE),

    // Alternate keys (@, !, :)
    AT_ANNOTATION_BUTTON('@', 64, 2, 9, Status.ACTIVE), EXCLAMATION_MARK_BUTTON('!', 33, 3, 9, Status.ACTIVE), COLON_BUTTON(':', 58, 3, 9, Status.ACTIVE);

    private char keyChar;
    private int primaryCode;
    private int row;
    private int column;
    private Status status;

    Key(char keyChar, int primaryCode, int row, int column, Status status) {
        this.keyChar = keyChar;
        this.primaryCode = primaryCode;
        this.row = row;
        this.column = column;
        this.status = status;
    }

    public char getKeyChar() {
        return keyChar;
    }

    public int getPrimaryCode() {
        return primaryCode;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Status getStatus() {
        return status;
    }

}
