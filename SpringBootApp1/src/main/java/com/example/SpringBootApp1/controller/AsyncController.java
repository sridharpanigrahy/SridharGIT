package com.example.SpringBootApp1.controller;

import com.example.SpringBootApp1.model.Greeting;
import com.example.SpringBootApp1.service.AysncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
public class AsyncController
{
    @Autowired
    AysncService aysncService;

    // https://howtodoinjava.com/spring-boot2/rest/enableasync-async-controller/
    // https://spring.io/guides/gs/async-method/
    @RequestMapping("/getEmpGreetingsUsingAsync")
    Map<String, List<? extends  Greeting>> getEmpGreetings() throws ExecutionException, InterruptedException
    {
        log.info("Thread Name inside getEmpGreetings - controller is " + Thread.currentThread().getName());
        Map<String, List<? extends  Greeting>> stringListMap = new LinkedHashMap<>();
        stringListMap.put("Start Time " + LocalDateTime.now().toString(), null);

        CompletableFuture<List<Greeting>> listCompletableFuture = aysncService.fetchEmpGreetings();
        CompletableFuture<String> stringCompletableFuture = aysncService.getCurrentTimeUsingAsync();
        // Wait until they are all done
        // CompletableFuture.allOf(employeeAddress, employeeName, employeePhone).join();
        CompletableFuture.allOf(listCompletableFuture, stringCompletableFuture).join();

        stringListMap.put(stringCompletableFuture.get(), listCompletableFuture.get());
        stringListMap.put("End Time " + LocalDateTime.now().toString(), null);
        return stringListMap;

    }
}
