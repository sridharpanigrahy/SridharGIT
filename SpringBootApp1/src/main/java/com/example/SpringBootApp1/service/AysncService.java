package com.example.SpringBootApp1.service;

import com.example.SpringBootApp1.model.Emp;
import com.example.SpringBootApp1.model.Greeting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AysncService
{
    @Autowired
    @Qualifier("restTemplate1")  /* Qualify one using name of bean. */
    private RestTemplate restTemplate;

    @Async("asyncExecutor")
    public CompletableFuture<List<Greeting>> fetchEmpGreetings() throws InterruptedException
    {
        final String ROOT_URI = "http://localhost:8080/spring1/employees";
        log.info("Thread Name inside fetchEmpGreetings Service is " + Thread.currentThread().getName());
        List<Greeting> greetingList = new ArrayList<>();
        Emp [] empList = restTemplate.getForObject(ROOT_URI, Emp[].class);
        /*
            ResponseEntity<Emp[]> response = restTemplate.getForEntity(ROOT_URI, Emp[].class);
		    return Arrays.asList(response.getBody());
         */
        Arrays.asList(empList).forEach(emp->{
            greetingList.add(new Greeting(emp.getEmpId(), emp.getFname()));
        });
        TimeUnit.SECONDS.sleep(10);

        return  CompletableFuture.completedFuture(greetingList);
    }

    @Async("asyncExecutor")
    public CompletableFuture<String> getCurrentTimeUsingAsync() throws InterruptedException
    {
        TimeUnit.SECONDS.sleep(15);
        return CompletableFuture.completedFuture(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).toString());
    }
}
