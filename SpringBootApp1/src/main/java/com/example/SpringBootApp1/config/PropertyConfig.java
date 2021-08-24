package com.example.SpringBootApp1.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Slf4j
@Configuration
@PropertySource(value = "classpath:error.properties", ignoreResourceNotFound = true)
@Data
public class PropertyConfig
{
    @Autowired
    Environment environment;

    @Value("${user.msg.fname}")
    String fnameError;

    @Value("${user.msg.lname}")
    String lnameError;

    @Value("${salary}")
    int salary;

    @Value("${fNameLNameError: Error for fNameLNameError as default message}")
    String fNameLNameError;

    public void printPropertiesUsingEnv()
    {
        log.info("Fname Message is : " + environment.getProperty("user.msg.fname"));
        log.info("Lname Message is : " + environment.getProperty("user.msg.lname"));
        log.info("Salary Message is : " + environment.getProperty("salary"));
        log.info("fNameLNameError Message is : " + environment.getProperty("fNameLNameError","NOT FOUND"));
    }
}
