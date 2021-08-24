package com.example.demo.basic;

import lombok.*;
import lombok.extern.java.Log;

@Setter
@Getter
@Log
public class Emp extends Person {

    int salary;
    String designation;
    public Emp(String name, int age, int salary, String designation)
    {
        super(name, age);
        this.salary= salary;
        this.designation = designation;
    }

    public void display()
    {
        log.info("Emp Details are - " + this.toString() );
    }

    @Override
    public String toString()
    {
        return String.format("Name: %s , Age: %d , Salary: %d",name,age,salary);
    }
}
