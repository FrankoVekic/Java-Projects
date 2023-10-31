package com.dogs;

public class Animal {

    private Gender gender;
    private int age;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;

    }

    public void setAge(int age) {
        if (age < 0) {
            return;
        }

        this.age = age;
    }

}
