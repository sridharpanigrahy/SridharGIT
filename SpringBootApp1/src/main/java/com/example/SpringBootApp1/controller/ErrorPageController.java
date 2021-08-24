package com.example.SpringBootApp1.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrorPageController implements ErrorController
{
    @RequestMapping("/error")
    public String errorPageDisplay(HttpServletRequest request)
    {
        String errorMsg = String.format("<b>The request page is not found.</b>");
        return errorMsg;
    }
}
