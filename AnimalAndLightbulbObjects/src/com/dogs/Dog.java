package com.dogs;

public class Dog extends Animal {

    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return getName() + " " + this.getGender();
    }

}
