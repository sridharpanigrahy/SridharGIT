package com.example.SpringBootApp1.controller;

import com.example.SpringBootApp1.config.PropertyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertyViewController
{
    @Autowired
    PropertyConfig propertyConfig;

    @GetMapping("/getPropertiesFromErrorFile")
    public ResponseEntity<String> getPropertiesFromErrorFile()
    {
        propertyConfig.printPropertiesUsingEnv();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<br>Fname Message : " + propertyConfig.getFnameError());
        stringBuilder.append("<br>Lname Message : " + propertyConfig.getLnameError());
        stringBuilder.append("<br>Salary Message : " + propertyConfig.getSalary());
        stringBuilder.append("<br>Default Message since property is missing : " + propertyConfig.getFNameLNameError());
        return ResponseEntity.ok(stringBuilder.toString());
    }
}
