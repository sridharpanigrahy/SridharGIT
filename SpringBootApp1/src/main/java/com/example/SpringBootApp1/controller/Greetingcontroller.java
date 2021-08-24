package com.example.SpringBootApp1.controller;

import com.example.SpringBootApp1.model.Greeting;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class Greetingcontroller
{
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(path = "/greetings", method = RequestMethod.GET)
    Greeting getGreeting(@RequestParam(name = "content", defaultValue = " World") String content)
    {
        return new Greeting(counter.incrementAndGet(), content) ;
    }

    @GetMapping(path = "/hello")
    String getHello()
    {
        return "<b>Hello Spring Boot<b>";
    }
}
