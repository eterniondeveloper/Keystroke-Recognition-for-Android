package com.raptis.konstantinos.keystrokerecognitionforandroid.util;

/**
 * Created by konstantinos on 17/4/2016.
 */
public enum Key {

    // Line 1
    ONE_BUTTON('1', 1049, 0, 0, Status.ACTIVE), TWO_BUTTON('2', 1050, 0, 1, Status.ACTIVE),
    THREE_BUTTON('3', 1051, 0, 2, Status.ACTIVE), FOUR_BUTTON('4', 1052, 0, 3, Status.ACTIVE),
    FIVE_BUTTON('5', 1053, 0, 4, Status.ACTIVE), SIX_BUTTON('6', 1054, 0, 5, Status.ACTIVE),
    SEVEN_BUTTON('7', 1055, 0, 6, Status.ACTIVE), EIGHT_BUTTON('8', 1056, 0, 7, Status.ACTIVE),
    NINE_BUTTON('9', 1057, 0, 8, Status.ACTIVE), ZERO_BUTTON('0', 1048, 0, 9, Status.ACTIVE),

    // Line 2
    Q_BUTTON('q', 1113, 1, 0, Status.ACTIVE), W_BUTTON('w', 1119, 1, 1, Status.ACTIVE),
    E_BUTTON('e', 1101, 1, 2, Status.ACTIVE), R_BUTTON('r', 1114, 1, 3, Status.ACTIVE),
    T_BUTTON('t', 1116, 1, 4, Status.ACTIVE), Y_BUTTON('y', 1121, 1, 5, Status.ACTIVE),
    U_BUTTON('u', 1117, 1, 6, Status.ACTIVE), I_BUTTON('i', 1105, 1, 7, Status.ACTIVE),
    O_BUTTON('o', 1111, 1, 8, Status.ACTIVE), P_BUTTON('p', 1112, 1, 9, Status.ACTIVE),

    // Line 3
    A_BUTTON('a', 1097, 2, 0, Status.ACTIVE), S_BUTTON('s', 1115, 2, 1, Status.ACTIVE),
    D_BUTTON('d', 1100, 2, 2, Status.ACTIVE), F_BUTTON('f', 1102, 2, 3, Status.ACTIVE),
    G_BUTTON('g', 1103,2 , 4, Status.ACTIVE), H_BUTTON('h', 1104, 2, 5, Status.ACTIVE),
    J_BUTTON('j', 1106, 2, 6, Status.ACTIVE), K_BUTTON('k', 1107, 2, 7, Status.ACTIVE),
    L_BUTTON('l', 1108, 2, 8, Status.ACTIVE), SHARP_BUTTON('#', 1035, 2, 9, Status.ACTIVE),

    // Line 4
    CAPS_LOCK_BUTTON('↑', 999, 3, 0, Status.INACTIVE), Z_BUTTON('z', 1122, 3, 1, Status.ACTIVE),
    X_BUTTON('x', 1120, 3, 2, Status.ACTIVE), C_BUTTON('c', 1099, 3, 3, Status.ACTIVE),
    V_BUTTON('v', 1118, 3, 4, Status.ACTIVE), B_BUTTON('b', 1098, 3, 5, Status.ACTIVE),
    N_BUTTON('n', 1110, 3, 6, Status.ACTIVE), M_BUTTON('m', 1109, 3, 7, Status.ACTIVE),
    FULL_STOP_BUTTON('.', 1046, 3, 8, Status.ACTIVE), QUESTION_MARK_BUTTON('?', 1063, 3, 9, Status.ACTIVE),

    // Line 5
    COMMA_BUTTON(',', 1044, 4, 0, Status.ACTIVE), SLASH_BUTTON('/', 1047, 4, 1, Status.ACTIVE),
    SPACE_BUTTON(' ', 1032, 4, 2, Status.INACTIVE), DELETE_BUTTON('←', 995, 4, 3, Status.INACTIVE),
    DONE_BUTTON('→', 996, 4, 4, Status.INACTIVE),

    // Alternate keys (@, !, :)
    AT_ANNOTATION_BUTTON('@', 1064, 2, 9, Status.ACTIVE), EXCLAMATION_MARK_BUTTON('!', 1033, 3, 9, Status.ACTIVE),
    COLON_BUTTON(':', 1058, 3, 9, Status.ACTIVE);

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
