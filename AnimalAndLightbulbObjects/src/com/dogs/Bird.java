package com.dogs;

public class Bird extends Animal {

    private double beakLength;
    
    
    public void setBeakLength(double beakLength){
        this.beakLength = beakLength;
    }
    
    public double getBeakLength(){
        return beakLength;
    }

        @Override
    public String toString() {
        return "Animal{" + "gender=" + getGender() + ", age=" + getAge() + ", name=" + getName() + ", beakLength=" + beakLength + '}';
    }
    
    
    
}
