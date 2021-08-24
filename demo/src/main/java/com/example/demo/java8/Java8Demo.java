package com.example.demo.java8;

import com.example.demo.basic.Emp;
import com.example.demo.basic.Person;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import static java.lang.System.out;   // Importing the out variable from  System class.
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Data
@Log
public class Java8Demo {

    final private Consumer printConsumer = s-> out.print(" " + s);
    final private BiConsumer printBiConsumer = (s1, s2)-> out.println(s1 + " " + s2);

    public Java8Demo()
    {
        streamDemo();
        java8DateDemo();
        java8String();
        java8FileDemo();
        java8OptionalsDemo();
    }

    private List<Emp> generateEmpList()
    {
        List<Emp> empList = Arrays.asList(new Emp("ABC1",10,10000,"ED"),
                new Emp("ABC2",20,20000,"MD"),
                new Emp("ABC3",30,30000,"ED"),
                new Emp("ABC4",40,40000,"AD"),
                new Emp("ABC5",50,50000,"AD"),
                new Emp("ABC6",60,60000,"ED"),
                new Emp("ABC7",70,70000,"MD"),
                new Emp("ABC8",80,80000,"MD"),
                new Emp("ABC9",90,90000,"CEO")
        );
        return empList;
    }

    public void streamDemo()
    {
        List<Emp> empList = generateEmpList();
        log.info("Stream and foreach to print the data...");
        empList.forEach(System.out::println);
        empList.stream().forEach(System.out::println);
        empList.stream().forEachOrdered(System.out::println);

        log.info("Parallel Stream and foreach to print the data...");
        empList.parallelStream().forEach(System.out::println);

        log.info(String.format("Length of List is : %d",empList.stream().count()));

        log.info("Stream and Filtering with Salary > 30000...");
        empList.stream().filter(e -> e.getSalary()>30000).collect(Collectors.toList()).forEach(System.out::println);

        log.info("Employee name whose Salary > 50000...");
        empList.stream().filter(e->e.getSalary()>50000).map(emp-> emp.getName()).collect(Collectors.toList()).forEach(System.out::println);

        log.info("Converting emp list to emp age list and print them.");
        empList.stream().mapToInt(emp->emp.getAge()).forEach(System.out::println);

        log.info("Printing first employee name...");
        Optional<Emp> empOptional =  empList.stream().findFirst();
        empOptional.ifPresent(e -> System.out.println("First employee name is :" + e.getName()));

        log.info("Printing any employee name or warning message.");
        empList.stream().findAny().ifPresentOrElse(e -> System.out.println("Any Employee name is: " + e.getName()), ()-> System.out.println("No employee found."));

        log.info("Printing first 5 employees...");
        empList.stream().limit(5).forEach(System.out::println);

        log.info("Printing max salary employee :");
        empList.stream().max(Comparator.comparing(Emp::getSalary)).ifPresent(System.out::println);

        log.info("Printing min salary employee :");
        empList.stream().min((e1, e2) -> {
            return e1.getSalary()-e2.getSalary();
        }).ifPresent(System.out::println);

        log.info("Stream peek as intermediate operation and find first..");
        empList.stream().peek(emp -> emp.display()).filter(e -> e.getSalary()>120000).findFirst();

        log.info("Converting String to upper case and print using peek");
        Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase).forEach(System.out::println);

        log.info("Stream with flat map...");
        Stream.of(Arrays.asList(1,2,3),Arrays.asList(4,5,6,8)).flatMap(n -> n.stream()).forEach(System.out::println);

        log.info("Emp list Group by designation..");
        empList.stream().collect(Collectors.groupingBy(Emp::getDesignation)).forEach((k,v) -> {
            System.out.println("Key: " + k + " Values are : " + v.toString());
        });

        log.info("Emp list count Group by designation..");
        empList.stream().collect(Collectors.groupingBy(Emp::getDesignation,Collectors.counting())).forEach(
                (k,v) ->
                {
                    System.out.println("Designation:" + k + " Emp Count:" + v);
                }
        );

        log.info("Emp total salary Group by designation..");
        empList.stream().collect(Collectors.groupingBy(Emp::getDesignation,Collectors.summarizingInt(Emp::getSalary))).forEach(
                (k,v) ->
                {
                    System.out.println("Designation:" + k + " Total Salary Designation Wise:" + v.getSum());
                    System.out.println("Designation:" + k + " Total Salary Designation Wise:" + v.getAverage());
                    System.out.println("Designation:" + k + " Total Salary Designation Wise:" + v.getMax());
                    System.out.println("Designation:" + k + " Total Salary Designation Wise:" + v.getMin());
                }
        );

        // https://mkyong.com/java8/java-8-collectors-groupingby-and-mapping-example/
        log.info("Emp count designation wise in sorted order");
        empList.stream().collect(Collectors.groupingBy(Emp::getDesignation,Collectors.counting()))
                .entrySet().stream().sorted(Map.Entry.<String,Long>comparingByValue().reversed()).forEach(System.out::println);

        System.out.println("Sum of Salary for all employees are : " + empList.stream().collect(Collectors.summingInt(Emp::getSalary)));
        //Stream using Reduce
        /*
            Identity – an element that is the initial value of the reduction operation and the default result if the stream is empty
            Accumulator – a function that takes two parameters: a partial result of the reduction operation and the next element of the stream
            Combiner – a function used to combine the partial result of the reduction operation when the reduction is parallelized or when there's a mismatch between
            the types of the accumulator arguments and the types of the accumulator implementation
         */
        System.out.println("Sum of Salary for all employees are : " + empList.stream().mapToInt(Emp::getSalary).reduce(0,(sum,ele) -> sum=sum+ele));

        List<String> letters = Arrays.asList("a", "b", "c", "d", "e");
        String result = letters.stream().reduce("", (partialString, element) -> partialString + element);
        System.out.println("String is:" + result);

        /* Printing 2 table using range function */
        IntStream.range(1,10).forEach(x->System.out.println(x*2));

        /* Using Stream 2 times.  */
        log.info("Using Stream 2 times...");
        Supplier<Stream> streamSupplier = () -> empList.stream();
        streamSupplier.get().limit(1).forEach(System.out::println);
        streamSupplier.get().limit(2).forEach(System.out::println);

    }

    /*
        https://mkyong.com/tutorials/java-8-tutorials/
        Java 8 created a series of new date and time APIs in java.time package. (JSR310 and inspired by Joda-time).

        java.time.LocalDate – date without time, no time-zone.
        java.time.LocalTime – time without date, no time-zone.
        java.time.LocalDateTime – date and time, no time-zone.
        java.time.ZonedDateTime – date and time, with time-zone.
        java.time.DateTimeFormatter – formatting (date -> text), parsing (text -> date) for java.time.
        java.time.Instant – date and time for machine, seconds passed since the Unix epoch time (midnight of January 1, 1970 UTC)
        java.time.Duration – Measures time in seconds and nanoseconds.
        java.time.Period – Measures time in years, months and days.
        java.time.TemporalAdjuster – Adjust date.
        java.time.OffsetDateTime – {update me}
     */
    public void java8DateDemo()
    {
        log.info("Java 8 Demo for DateTime..." );
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.systemDefault()));
        log.info("List of TimeZone Ids...");
        //Arrays.stream(TimeZone.getAvailableIDs()).collect(Collectors.toList()).forEach(System.out::println);

        //Printing today's date and time
        LocalDate today = LocalDate.now();
        System.out.println("Today's date is  : " + today.toString());
        System.out.println("Today's datetime is  : " + LocalDateTime.now(Clock.systemDefaultZone()).toString());
        System.out.println("Today's datetime as UTC is  : " + LocalDateTime.now(Clock.systemUTC()).toString());

        LocalDateTime l1 = LocalDateTime.of(2021,7,10,10,12,11);
        LocalDateTime l2 = LocalDateTime.of(2021,7,13,10,13,11);
        System.out.println("Is L1 before L2 : " + l1.isBefore(l2));
        System.out.println("Is L1 after L2 : " + l1.isAfter(l2));

        long epoch = Instant.now().toEpochMilli();
        System.out.println("Current time in epoch  : " + Instant.now().toEpochMilli());
        System.out.println("Current Date Time from epoch  : " + Instant.ofEpochMilli(epoch).atZone(ZoneId.systemDefault()).toLocalDateTime());

        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        System.out.println("Zoned Date Time in UTC : " + ZonedDateTime.now(ZoneId.of("UTC")).format(dateFormatter).toString());

        Clock clock1 = Clock.systemUTC();
        Clock clock2 = Clock.systemDefaultZone();
        System.out.println("Clock 1 as system UTC :" + clock1.instant().toString());
        System.out.println("Clock 2 as system Timezone :" + clock2.instant().toString());

        LocalDate endOfCentury = LocalDate.of(2014, 01, 01);
        LocalDate now = LocalDate.now();
        Period diff = Period.between(endOfCentury, now);
        System.out.printf("Difference is %d years, %d months and %d days old", diff.getYears(), diff.getMonths(), diff.getDays());

        Month month =  Month.NOVEMBER;
        out.println(String.format("The month variable value is : %s",month.getValue()));
        Arrays.stream(Month.values()).forEach(out::println);
        for (Month mon: Month.values()) {
            System.out.print( " " + mon);
        }
    }

    public void java8String()
    {
        log.info("Java 8 Demo for String..." );
        StringJoiner sj = new StringJoiner(","," PREFIX "," SUFFIX ");
        sj.add("ABCD").add("PQR");
        System.out.println("String joiner final string is :" + sj.toString());

        System.out.println("List of Emp Designation without any separator: " + generateEmpList().stream().map(Emp::getDesignation).collect(Collectors.joining()));
        System.out.println("List of Emp Designation with separator : " + generateEmpList().stream().map(Emp::getDesignation).collect(Collectors.joining("/")));
        System.out.println("List of Emp Designation with separator and prefix and suffix : " + generateEmpList().stream().map(Emp::getDesignation).collect(Collectors.joining("/","{","}")));

        System.out.print("Repeating String n times : \n" + String.join("\n", Collections.nCopies(5, "Mkyong")));

        /* Java 11 String Repeation */
        String str = "Mkyong\n";
        System.out.println("String repetition using Java 11 functionality :" + str.repeat(4));

        // String Comparison
        String s1 = "abc";
        String s2 = "abc";  /*  s1 == s2 will be true due to String Pool. */
        String s3 = new String("abc");
        String s4 = s3.intern(); // Find out the internal memory reference if present.
        System.out.println(String.format("String Comparison result for s1 == s2 is : %s ", s1 == s2));
        System.out.println(String.format("String Comparison result for s1 == s2 is : %s ", s1 == s3));
        System.out.println(String.format("String Comparison result for s1 == s2 is : %s ", s1 == s4));
    }

    /*
    https://mkyong.com/java/java-how-to-read-a-file/
    Files.lines, return a Stream (Java 8)
    Files.readString, returns a String (Java 11), max file size 2G.
    Files.readAllBytes, returns a byte[] (Java 7), max file size 2G.
    Files.readAllLines, returns a List<String> (Java 8)
    BufferedReader, a classic old friend (Java 1.1 -> forever)
    Scanner (Java 1.5)
     */
    public void java8FileDemo()
    {
        log.info("Java 8 Demo for File Handling..." );

        try(Stream<String> fileStream = Files.lines(Paths.get("D://logs/mylog.log")))
        {
            File file = ResourceUtils.getFile("classpath:logback.xml");

            log.info("File Name using Resource Utils : " + file.getAbsolutePath());
            String fileName = getClass().getClassLoader().getResource("logback.xml").getFile();
            log.info("File Name using ClassLoader : " + fileName);
            fileStream.limit(3).forEach(System.out::println);

        }
        catch (ArrayIndexOutOfBoundsException | IOException e)
        {
            e.printStackTrace();
            log.warning("Exception Found" + e.toString());
        }
    }

    public void java8OptionalsDemo()
    {
        log.info("Java 8 Demo for Optional..." );
        Person person1 = new Emp("ABC1",10,2000,"ED");
        Person person2 = null;

        Optional<Person> optionalPerson1 =  Optional.of(person1); // May throw NullPointerException as it is using Objects.requireNotNull method before assigning.
        Optional<Person> optionalPerson2 =  Optional.ofNullable(person2);  // Safe from NullPointerException

        optionalPerson1.ifPresentOrElse(out::println,() -> out.println("No value present for Person1 object."));
        optionalPerson2.ifPresentOrElse(out::println,() -> out.println("No value present for Person2 object."));

        optionalPerson1.ifPresent(out::println);
        optionalPerson2.ifPresent(out::println);

        optionalPerson2.orElseGet(() -> null);
        Person p1 = optionalPerson1.get();
        p1.display();

        String nullName = null;
        String name1 = Optional.ofNullable(nullName).orElse("john1");
        String name2 = Optional.ofNullable(nullName).orElseGet(()-> "john2");
        printConsumer.accept("\nName using optional orElse/orElseGet is :" + name1 + " , " + name2 );

        try
        {
            Optional.ofNullable(nullName).orElseThrow();
        }
        catch (Exception exception)
        {
            printConsumer.accept("\nException raised by orElseThrow :" + exception.getMessage());
        }

        List<String> companyNames = Arrays.asList("paypal", "oracle", "", "microsoft", "", "apple");
        Optional<List<String>> listOptional = Optional.of(companyNames);
        int size = listOptional.map(List::size).orElse(0);
        printConsumer.accept("List size is :" + size);

        Optional<String> found = Stream.<Optional<String>>of(Optional.ofNullable(null),Optional.ofNullable("PQR"),Optional.ofNullable(null))
                .filter((opt) -> opt.isPresent())
                .map((opt) -> opt.orElse("DEFAULT"))
                .findFirst();
        printConsumer.accept("\nFound Optional value is :" + found.get());

    }

}
