package com.example.tacademy.sampledatabase;

/**
 * Created by Tacademy on 2016-08-11.
 */
public class Person {

    long id = -1;
    String name;
    int age;
    String phone;
    String address;

    @Override
    public String toString() {
        return name + "(" + age + ")";
    }
}
