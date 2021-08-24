package com.example.SpringBootApp1.controller;

import com.example.SpringBootApp1.service.SpringRetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RetryController
{
    @Autowired
    SpringRetryService springRetryService;

    @GetMapping("/getNumberUsingSpringRetry1")
    public ResponseEntity<Long> getNumberUsingSpringRetryOne()
    {
        Long num1= springRetryService.getNumberDividedByTen1();
        return ResponseEntity.ok(num1);
    }

    @GetMapping("/getNumberUsingSpringRetry2")
    public ResponseEntity<Long> getNumberUsingSpringRetryTwo()
    {
        Long num2= springRetryService.getNumberDividedByTen2();
        return ResponseEntity.ok(num2);
    }
}
