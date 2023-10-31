package com.light;

public class LightBulb {

    private int intensity;
    private String color;
    private boolean active;

    public LightBulb(String color, boolean active) {
        this.color = color;
        this.active = active;
    }

    public void setIntensity(int intensity) {
        if(intensity<0 || intensity>100){
            System.out.println("Please don't try to break my lightbult");
            System.out.println("Value should be from 0-100");
            return;
        }
        this.intensity = intensity;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getIntensity() {
        return intensity;
    }

    public String getColor() {
        return color;
    }

    public boolean isActive() {
        return active;
    }

}
