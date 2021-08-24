package com.example.demo.basic;

public class Basic
{
    public Basic()
    {
        Person person = new Emp("Sridhar",40,45000,"ED");
        final Emp e = new Emp("abc",30,20000,"MD");
        e.display();
        e.setName("ABCD");
        e.display();
    }
}
