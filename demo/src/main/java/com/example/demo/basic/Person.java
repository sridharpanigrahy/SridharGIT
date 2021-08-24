package com.example.demo.basic;

import lombok.*;

@Data
@ToString
abstract public class Person {
    String name;
    int age;

   public Person(String name, int age) {
       this.name = name;
       this.age = age;
   }

    abstract public void display();

}
