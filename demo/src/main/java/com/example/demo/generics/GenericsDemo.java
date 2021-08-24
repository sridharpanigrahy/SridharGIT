package com.example.demo.generics;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// https://www.journaldev.com/1663/java-generics-example-method-class-interface
// https://howtodoinjava.com/java/generics/complete-java-generics-tutorial/

// Here, T will considered as object. It can not be primitive data types.
class DisplayGenerics<T>
{
    // Generics Method.
    <T> void displayGenericsData(T val)
    {
        String className = val.getClass().getName();
        System.out.println("Value of type [ " + className + " ] is " + val);
    }

    // Generics with Upper Bound
    /*
      Allowed type will be : Number or any other child class of Number like Integer, Double

      Suppose, we want to write the method for the list of Number and its subtypes (like Integer, Double).
      Using List<? extends Number> is suitable for a list of type Number or any of its subclasses whereas List<Number> works with the
      list of type Number only. So, List<? extends Number> is less restrictive than List<Number>.

      GenericType<? extends SuperClass>

     */
    void sumListUsingUpperBound(List<? extends Number> list)
    {
        if (list == null)
        {
            return;
        }

        Objects.requireNonNull(list,"ERROR: List is NULL...");
        Double sum = 0.0;
        for (Number n: list)
        {
            sum = sum + n.doubleValue();
        }
        System.out.println("Sum of List using Generics Upper Bound Generics :" + sum);
    }

    // Generics with wild card - any type of Object is allowed here.
    /*
        The unbounded wildcard type represents the list of an unknown type such as List<?>. This approach can be useful in the following scenarios: -

        When the given method is implemented by using the functionality provided in the Object class.
        When the generic class contains the methods that don't depend on the type parameter.

     */
    void displayWileCardList(List<?> list)
    {
        System.out.print("List of values are using wildcard generics :\n");
        for (Object o : list)
        {
            System.out.println(o.toString());
        }
    }

    // Generics with Lower Bound
    /*
      Allowed type will be : Integer or any other parent class of Integer like Number.

      Lower Bounded Wildcards
        The purpose of lower bounded wildcards is to restrict the unknown type to be a specific type or a supertype of that type.
        It is used by declaring wildcard character ("?") followed by the super keyword, followed by its lower bound.

        Syntax: List<? super Integer>
        Here,

            ? is a wildcard character.
            super, is a keyword.

            Integer, is a wrapper class.

        Suppose, we want to write the method for the list of Integer and its supertype (like Number, Object).
        Using List<? super Integer> is suitable for a list of type Integer or any of its superclasses whereas List<Integer> works with the list of
        type Integer only. So, List<? super Integer> is less restrictive than List<Integer>.

         GenericType<? super SubClass>
     */
    void sumListUsingLowerBound(List<? super Integer> list)
    {
        if (list == null)
        {
            return;
        }

        // list.add(Double.valueOf("10.10")); // will give compilation error as Double is not super of Integer.
        // list.add(Integer.valueOf("10"));
        Objects.requireNonNull(list,"ERROR: List is NULL...");
        Double sum = 0.0;
        for (Object n: list)
        {
            sum = sum + ((Number) n).doubleValue();
        }
        System.out.println("Sum of List using Generics lower Bound Generics :" + sum);
    }
}

public class GenericsDemo
{
    public GenericsDemo()
    {
        executeGenericsDemo();
    }

    public void executeGenericsDemo()
    {
        DisplayGenerics displayVar = new DisplayGenerics<>();
        displayVar.displayGenericsData("Hello World");
        displayVar.displayGenericsData(12345);
        displayVar.displayGenericsData(12345.23456);
        displayVar.displayGenericsData(Arrays.asList("abc","pqr","xyz"));
        displayVar.<String>displayGenericsData("Specifying the String type for method.");

        List<Number> numberList = Arrays.asList(10,20,30,40,50);
        List<Double> doubleList = Arrays.asList(10.12,20.34,30.3,40.4,50.5);
        List<Integer> integerList = Arrays.asList(10,20,30,40,50);
        displayVar.sumListUsingUpperBound(numberList);
        displayVar.sumListUsingUpperBound(doubleList);
        displayVar.sumListUsingUpperBound(integerList);
        displayVar.sumListUsingUpperBound(null);

        displayVar.displayWileCardList(Arrays.asList("abc","xyz","100",30,50));

        List<? extends Integer> intList = new ArrayList<>();
        List<? extends Number>  numList = intList;  // OK. List<? extends Integer> is a subtype of List<? extends Number>
        displayVar.sumListUsingLowerBound(doubleList);
        displayVar.sumListUsingLowerBound(integerList);
        displayVar.sumListUsingLowerBound(numberList);
        List<Long> longList = Arrays.asList(10L,20L,30L,40L,50L);
        displayVar.sumListUsingLowerBound(longList);

    }
}
