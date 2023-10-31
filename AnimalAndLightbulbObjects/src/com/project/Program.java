package com.project;

import com.dogs.*;
import com.light.*;

public class Program {

    public static void main(String[] args) {

        Dog rex = new Dog();
        rex.setName("rex");

        rex.setAge(9);
        rex.setColor(Color.GREEN);
        rex.setGender(Gender.MALE);
        rex.setName("Rex");

        Bird tweety = new Bird();
        tweety.setBeakLength(1.2);
        tweety.setName("Tweety");
        tweety.setAge(5);
        tweety.setGender(Gender.FEMALE);

        System.out.println(tweety.toString());
        System.out.println(rex.toString());

//        LightBulb kitchen = new LightBulb("Red", true);
//        
//        kitchen.setIntensity(100);
//        
//        kitchen.setActive(false);
//        
//        Program.printLightBulb(kitchen);
//        Dog max = new Dog("Max", "shih tzu", 5);
//
//        Dog rex = new Dog("Rex", "poodle", 7);
//
//        Dog fido = new Dog("Fido", "labrador", 6);
//        
//        Dog zizi = new Dog("Zizi", "stafford");
//
//        printDog(max);
//        printDog(rex);
//        printDog(fido);
//        printDog(zizi);
    }

//    public static void printLightBulb(LightBulb l){
//        System.out.printf("Color: %s%nIntensity: %d%nActive: %s%n", l.getColor(), l.getIntensity(), l.isActive() );
//    }
//
//    public static void printDog(Dog d) {
//        System.out.println("");
//        System.out.printf("Dog name: %s%nBreed: %s%nAge: %d%n", d.name, d.breed, d.age);
//    }
}
