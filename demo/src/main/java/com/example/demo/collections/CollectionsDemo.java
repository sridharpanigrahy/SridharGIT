package com.example.demo.collections;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.extern.java.Log;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.out;

/*
    https://www.studytonight.com/java/collection-framework.php
    https://www.javatpoint.com/collections-in-java
    https://howtodoinjava.com/java-collections/
    https://www.javaguides.net/2018/08/collections-framework-in-java.html
 */
@Log
public class CollectionsDemo
{
    final private Consumer printConsumer = s-> out.print(" " + s);
    final private BiConsumer printBiConsumer = (s1,s2)-> out.println(s1 + " " + s2);
    public CollectionsDemo()
    {
        demoOfArraysClass();
        arrayListDemo();
        splitIteratorDemo();
        linkedListDemo();
        setDemo();
        mapDemp();
        demoCollectionsClass();
    }

    List<String> getNameArrayList()
    {
        List<String> nameList = new ArrayList<>();
        nameList.add("ACM");
        nameList.add("DCPP");
        nameList.add("AME");
        nameList.add("AIM");
        nameList.add("Trade");
        nameList.add("ACM2");
        nameList.add("Trade-2");
        nameList.addAll(Arrays.asList("123","456","678"));
        return nameList;
    }

    LinkedList<String> getNameLinkList()
    {
        LinkedList<String> nameList = new LinkedList<>();
        nameList.add("ACM");
        nameList.add("DCPP");
        nameList.add("AME");
        nameList.add("AIM");
        nameList.add("Trade");
        nameList.add("ACM2");
        nameList.add("Trade-2");
        nameList.addAll(Arrays.asList("123","456","678"));
        return nameList;
    }

    void demoOfArraysClass()
    {
        log.info("Demo of Arrays Class...");
        List<String> nameList = Arrays.asList("abc","pqr");
        Integer [] numbersArray  = {10,30,20,15,100,95};
        Arrays.sort(numbersArray);
        Stream<Integer> stream = Arrays.stream(numbersArray);
        stream.collect(Collectors.toList()).forEach(out::print);
        out.println("New Array count :" + Arrays.stream(Arrays.copyOf(numbersArray,3)).count());

        Integer[] newNumArray = Arrays.copyOf(numbersArray,20);
        Arrays.stream(newNumArray).forEach(out::print);

    }

    void arrayListDemo()
    {
        log.info("Demo of ArrayList class ...");
        List<String> nameList = getNameArrayList();
        nameList.forEach(System.out::println);

        out.println("The value at Index 2 is : " + nameList.get(2));

        nameList.set(0, "ACM3");
        out.println("The value at Index 0 after sett is : " + nameList.get(0));

        /* Removing the name which as character as - */
        nameList.removeIf(str -> str.contains("-"));
        out.println("Printing the array list using Iterator :");
        Iterator<String> itr = nameList.iterator();
        while  (itr.hasNext())
        {
            out.print(" " + itr.next());
        }

        out.println("\nPrinting the array list using ListIterator :");
        ListIterator<String> listIterator = nameList.listIterator();
        while  (listIterator.hasNext())
        {
            out.print(" " + listIterator.next());
        }
        out.println("\nPrinting the array list using ListIterator in reverse direction :");
        while  (listIterator.hasPrevious())
        {
            out.print(" " + listIterator.previous());
        }
        out.println("\n");

        /* Converting the name array list object to array.*/
        Object []  nameArray = nameList.toArray();
        for (int i = 0; i < nameArray.length; i++)
        {
            out.print(" " + nameArray[i]);
        }

        /* Sort the array where the sort takes Comprator as argument. */
        nameList.sort((s1,s2) -> s1.compareTo(s2));
        out.println("After sorting of array :");
        nameList.forEach(out::print);

    }

    void splitIteratorDemo()
    {
        log.info("Demo of ArrayList class and SplitIterator ...");
        List<String> nameList = getNameArrayList();
        Spliterator<String> spliterator = nameList.spliterator();
        out.println("Estimated Size od Split Iterator : " + spliterator.estimateSize());
        out.println("Estimated Size od Split Iterator : " + spliterator.estimateSize());
        spliterator.forEachRemaining(out::print);

        /* Try to split the iterator. Can return null as well. */
        Spliterator<String> spliterator2 = spliterator.trySplit();
        if (spliterator2 != null)
            spliterator2.forEachRemaining(out::println);

    }

    void setDemo()
    {
        log.info("Demo of Set class ...");
        List<String> nameList = Arrays.asList("ACM1","ACM2","DCPP","Operate","ACM2","ACM1");
        Set<String> hashSet = new HashSet<>(nameList);
        hashSet.addAll(nameList);
        hashSet.add(null);
        // Creating Synchronized Set which will be thread safe.
        Set<String> synchronizedSet = Collections.synchronizedSet(hashSet);
        synchronizedSet.add("ACM5");
        synchronizedSet.remove("ACM1");

        out.println("Set Values are: ");
        hashSet.forEach(printConsumer);
        // Remove the elements from  hashset which are not exist in synchronizedSet.
        hashSet.retainAll(synchronizedSet);
        out.println("\nAfter Retain all, Set Values are: ");
        hashSet.forEach(printConsumer);

        out.println("\nAfter Sorting in natural order, Set Values are: ");
        hashSet.stream().sorted(Comparator.naturalOrder());
        hashSet.forEach(printConsumer);

        out.println("\nAfter Sorting in reverse order, Set Values are: ");
        hashSet.stream().sorted(Comparator.reverseOrder());
        hashSet.forEach(printConsumer);

        /*
        Java LinkedHashSet class is a Hash table and Linked list implementation of the Set interface.
        Contains unique elements only like HashSet.
        Provides all optional set operations, and permits null elements.
        Maintains insertion order.
        LinkedHashSet is not synchronized - If multiple threads access a linked hash set concurrently, and at least one of the threads modifies the set, it must be synchronized externally.
         */
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(nameList);
        Set<String> unmodSet  = linkedHashSet.stream().filter(str -> str.startsWith("ACM")).map(String::toUpperCase).collect(Collectors.toUnmodifiableSet());
        unmodSet.forEach(printConsumer);

        /*
        The TreeSet is much similar to the HashSet class. The major difference between both classes is used data structure between them.
        The HashSet uses a Hashtable, and the TreeSet uses a self-balancing tree.

        The TreeSet is a class of the Java Collection framework used to store the tree data structure. It uses a tree data structure to store and maintain the objects. The TreeSet class is the implementing class of the Set interface.

        The TreeSet stores the objects in the ascending order, which is a natural ordering of a tree.
        We can also specify a comparator to sort the elements based on it during the creation of the TreeSet.

        It contains unique elements like the HashSet class.
        It provides a faster way to access and retrieve elements.
        It does not allow to store the null elements.
        It is a non-synchronized class.
        It stores and maintains the elements in ascending order.
        The package for the TreeSet class is java.util
        It implements the SortedSet and NavigableSet interface to keep the elements sorted and structured.
        It also implements the Cloneable and Serializable interfaces.
        It extends the AbstractSet class.

        When we implement a TreeSet, it creates a TreeMap to store the elements. It sorts the elements either naturally or using the user define comparator.
        When the object of a TreeSet is created, it automatically invokes the default constructor and creates an object of TreeMap and assigns comparator as null.

        When we add an element to TreeMap, it compares each element with other elements and put it in TreeMap.
        It uses comparable to compare the objects. The put() method checks if the comparator object is null.
         */
        TreeSet<String> treeSet = new TreeSet<>(nameList);
        out.println("\nTreeSet Values are: ");
        treeSet.forEach(printConsumer);

        Iterator treeSetIt = treeSet.descendingIterator();
        out.println("\nUsing Descending, TreeSet Values are: ");
        while  (treeSetIt.hasNext())
        {
            out.print(" " + treeSetIt.next());
        }

        // treeSet.add(null);  It gives Runtime Exception as the null is not allowed.

        printBiConsumer.accept("TreeSet First Value is :", treeSet.first());

        // Retrieve and remove the first element from the TreeSet
        printBiConsumer.accept("TreeSet First Value is :", treeSet.pollFirst());
        printBiConsumer.accept("TreeSet Last Value is :", treeSet.last());

        // Retrieve and remove the last element from the TreeSet
        printBiConsumer.accept("TreeSet First Value is :", treeSet.pollLast());

        SortedSet<Integer> sortedSet = Collections.synchronizedSortedSet(new TreeSet<>(Arrays.asList(10,20,5,1,100,25,11)));
        printConsumer.accept("\nInteger TreeSet values are :");
        sortedSet.forEach(printConsumer);

        TreeSet<Integer> treeSetSorting = new TreeSet<>(Comparator.reverseOrder());
        treeSetSorting.addAll(Stream.of(20,1,200,195,5).collect(Collectors.toSet()));
        printConsumer.accept("\nInteger TreeSet values in reverse order are :");
        treeSetSorting.forEach(printConsumer);

        printConsumer.accept("\nThe greater value lower than asked value is:" + treeSetSorting.lower(10));  // 5
        printConsumer.accept("\nThe greater value higher than asked value is:" + treeSetSorting.higher(10)); // 11

        TreeSet<Student> treeSetStud = new TreeSet<>();
        treeSetStud.add(new Student(10,"ABC10"));
        treeSetStud.add(new Student(1,"ABC1"));
        treeSetStud.add(new Student(1100,"ABC100"));
        treeSetStud.add(new Student(220,"ABC20"));
        treeSetStud.add(new Student(210,"ABC21"));
        printConsumer.accept("\nTreeSet with Custom Object as Student are :" + treeSetStud.size());
        treeSetStud.forEach(printConsumer);

    }

    void linkedListDemo()
    {
        log.info("Demo of LinkedList ...");
        LinkedList<String> linkedList = getNameLinkList();
        linkedList.add("Hello");
        List<String> unmodList = linkedList.stream().collect(Collectors.toUnmodifiableList());
        unmodList.forEach(out::println);
        out.println(String.format("First and Last elements of Linked List are : %s , %s",linkedList.getFirst(),linkedList.getLast()));

        linkedList.get(0);
        linkedList.addFirst("First Index");
        linkedList.addLast("Last Index");
        linkedList.contains("ACM");
        linkedList.peekFirst(); // Peeks the first node.
        linkedList.clear();

    }

    void mapDemp()
    {
        log.info("Demo of Map...");

        /*
        HashMap in Java is a powerful data structure that allows us to store the key-pair values.
        It allows us to store the null value objects. We can not insert the duplicate key; if we try, it will replace that element corresponding to the respected key.

        It contains key-pair values.
        It can not have duplicate keys.
        It may have one null key and multiple null values.
        It is non-synchronized.
        It does not provide a way to maintain the order of elements.
        The default capacity of the Java HashMap class is 16 with a load factor of 0.75.

        A Map is an object that maps keys to values. Each key and value pair is known as an entry. The map contains only unique keys.
        A map cannot contain duplicate keys: Each key can map to at most one value.
        The Java platform contains three general-purpose Map implementations: HashMap, TreeMap, and LinkedHashMap.

        A HashMap contains values based on the key.
        It contains only unique elements.
        It may have one null key and multiple null values.
        It maintains no order.
        Java HashMap is not thread-safe. You must explicitly synchronize concurrent modifications to the HashMap.
         */
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"ABC");
        map.put(20,"PQR");
        map.put(3,"XYZ");
        // Add a new key-value pair only if the key does not exist in the HashMap, or is mapped to `null`
        map.putIfAbsent(1,"PQR");
        map.put(2,"QRT");
        map.forEach(printBiConsumer);
        map.entrySet().forEach(printConsumer);  // 1=ABC 2=QRT 3=XYZ 20=PQR

        // Generating Entry Collection where Map is interface and Entry is static interface inside Map interface.
        Set<Map.Entry<Integer,String>> mapEntries = map.entrySet();
        printConsumer.accept("\n Using Entry Set and forEach method.");
        mapEntries.stream().forEach(printConsumer);

        printConsumer.accept("\n Using Entry Set and for loop.");
        for (Map.Entry entry:mapEntries)
        {
            out.println(entry.getKey() + " - " + entry.getValue());
        }

        printConsumer.accept(String.format("\nIs Map empty : %s",map.isEmpty()));
        printConsumer.accept(String.format("\nMap size : %s",map.size()));
        printConsumer.accept(String.format("\nMap Get : %s",map.get(1)));
        printConsumer.accept(String.format("\nMap Get : %s",map.getOrDefault(10,"Sridhar")));

        // compute the key and value if key is not present.
        map.computeIfAbsent(10,s -> "Compute".toUpperCase(Locale.ROOT));
        map.forEach(printBiConsumer);

        printConsumer.accept("\n Printing Map Keys :");
        map.keySet().forEach(printConsumer);
        printConsumer.accept("\n Printing Map Values :");
        map.values().forEach(printConsumer);

        Map<Integer,String> synchronizedMap= Collections.synchronizedMap(map);
        printConsumer.accept(String.format("\nSynchronized Map size : %s",synchronizedMap.size()));

        /*
        Java LinkedHashMap class is Hash table and Linked list implementation of the Map interface, with predictable iteration order.
        It inherits the HashMap class and implements the Map interface.
        The important points about Java LinkedHashMap class are:
        A LinkedHashMap contains values based on the key.
        It contains only unique elements.
        It may have one null key and multiple null values.
        It is the same as HashMap instead maintains insertion order.
        Unlike HashMap, the iteration order of the elements in a LinkedHashMap is predictable.
        Just like HashMap, LinkedHashMap is not thread-safe. You must explicitly synchronize concurrent access to a LinkedHashMap in a multi-threaded environment.
         */
        LinkedHashMap<String,Student> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("s1", new Student(1,"S1"));
        linkedHashMap.put("s2", new Student(1,"S2"));
        linkedHashMap.forEach(printBiConsumer);

        printConsumer.accept("\n Using LinkHashMap and Iterator..");
        Iterator iterator = linkedHashMap.entrySet().iterator();
        while (iterator.hasNext())
        {
            Map.Entry<String,Student> entry = (Map.Entry<String,Student>) iterator.next();
            out.println(entry.getValue());
        }

        /*
        Java TreeMap class implements the Map interface by using a tree. It provides an efficient means of storing key/value pairs in sorted order.

        A TreeMap contains values based on the key. It implements the NavigableMap interface and extends AbstractMap class.
        It contains only unique elements.
        It cannot have a null key but can have multiple null values.
        It is same as HashMap instead maintains ascending order.
         */

        // Creating a TreeMap
        TreeMap<String, String> fileExtensions  = new TreeMap<>();

        fileExtensions.put("python", ".py");
        fileExtensions.put("c++", ".cpp");
        fileExtensions.put("kotlin", ".kt");
        fileExtensions.put("golang", ".go");
        fileExtensions.put("java", ".java");
        fileExtensions.put("key1", "value1");
        fileExtensions.put("key3", "value3");
        fileExtensions.put("key2", "value2");
        fileExtensions.put("key0", "value0");
        System.out.println(fileExtensions);
        System.out.println(fileExtensions.firstEntry());
        System.out.println(fileExtensions.lastEntry());
        System.out.println(fileExtensions.firstKey());
        System.out.println(fileExtensions.lastKey());

        // Creating a TreeMap with a Custom comparator (Descending order)
        SortedMap<String, Integer> numberWordMapping = new TreeMap<>(Comparator.reverseOrder());
        numberWordMapping.put("one", 1);
        numberWordMapping.put("two", 2);
        numberWordMapping.put("three", 3);
        numberWordMapping.put("five", 5);
        numberWordMapping.put("four", 4);

        System.out.println(numberWordMapping);
        numberWordMapping.replace("one",11);
        numberWordMapping.replaceAll((key,value) -> value*2);
        System.out.println(numberWordMapping);

        TreeMap<Integer, String> users1 = new TreeMap<>();
        users1.put(1003, "A");
        users1.put(1001, "B");
        users1.put(1002, "C");
        users1.put(1004, "D");
        // Find the entry whose key is just less than the given key
        Map.Entry<Integer, String> userLower = users1.lowerEntry(1002);
        // Find the entry whose key is just higher than the given key
        Map.Entry<Integer, String> userHigher = users1.higherEntry(1002);

        /*
        IdentityHashMap is a HashTable based implementation of Map Interface.
        Normal HashMap compares keys using '.equals' method.
        But Identity HashMap  compares its keys using '==' operator.
        Hence 'a' and new String('a') are considered as 2 different keys. The initial size of Identity HashMap is 21 while the initial size of normal HashMap is 16.
         */
        final IdentityHashMap<String, String> identityHashMap = new IdentityHashMap<String, String>();
        identityHashMap.put("a", "Java");
        identityHashMap.put(new String("a"), "J2EE");
        identityHashMap.put("b", "J2SE");
        identityHashMap.put(new String("b"), "Spring");
        identityHashMap.put("c", "Hibernate");
        System.out.println("IdentityHashMap : " + identityHashMap);

    }

    void demoCollectionsClass()
    {
        List<Integer> list = new ArrayList<>(Arrays.asList(10,1,2,0,100,99));
        Collections.sort(list);
        Collections.sort(list,Comparator.reverseOrder());
        Collections.reverse(list);
        Collections.max(list);
        Collections.min(list);
        List<Integer> destList = new ArrayList<>(list);
        Collections.copy(destList, list);
        Collections.shuffle(list);
    }

    /* Student Class implementing Comparable */
    @EqualsAndHashCode
    static class Student implements Comparable<Student>
    {
        int rollNo;
        String name;

        Student(int rollNo, String name)
        {
            this.rollNo = rollNo;
            this.name = name;
            //out.println(String.format("\nHashCode for RollNo %s is %s",this.rollNo, hashCode()));

        }
        @Override
        public int compareTo(Student student)
        {
            return this.rollNo-student.rollNo;
        }

        @Override
        public String toString()
        {
            return "\nRollNo:" + rollNo + " Name: " + name;
        }
    }

}
