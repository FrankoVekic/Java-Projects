package com.main.inputwindowtransferapp;

import java.io.Serializable;

public class User implements Serializable{

    String firstName;
    String lastName;
    int age;
    String gender;
    
    public User(String firstName, String lastName, int age, String gender){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

}
