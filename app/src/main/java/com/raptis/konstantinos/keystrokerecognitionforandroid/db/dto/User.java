package com.raptis.konstantinos.keystrokerecognitionforandroid.db.dto;

import java.io.Serializable;

/**
 * Created by konstantinos on 8/5/2016.
 */

public class User implements Serializable {

    // variables
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;

    // constructor
    public User() {
        this.username = null;
        this.password = null;
    }

    // constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // getters
    public String getPassword() {
        return password;
    }

    public String getUsername() { return username; }

    public String getTrainArffName() {
        return username + "_train.arff";
    }

    public String getTestArffName() {
        return username + "_test.arff";
    }

    // setters


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // to String
    @Override
    public String toString() {
        return String.format("%s", username);
    }
}

