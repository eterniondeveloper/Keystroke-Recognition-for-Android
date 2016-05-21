package com.raptis.konstantinos.keystrokerecognitionforandroid.db.dto;

import java.io.Serializable;

/**
 * Created by konstantinos on 8/5/2016.
 */

public class User implements Serializable {

    // variables
    private static final long serialVersionUID = 1L;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String arffName;

    // constructor
    public User() {
        this.email = null;
        this.password = null;
        this.firstName = null;
        this.lastName = null;
    }

    // constructor
    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // getters
    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getArffName() {
        return arffName;
    }

    // setters

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setArffName(String arffName) {
        this.arffName = arffName;
    }

    // to String
    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }
}

