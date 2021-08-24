package com.example.SpringBootApp1.config;

import lombok.Data;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "user.msg")
@Configuration
@Data
public class ErrorConfig
{
    String fname;
    String lname;
    String salary;
    String email;
}
