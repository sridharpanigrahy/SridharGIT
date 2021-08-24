package com.example.SpringBootApp1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j

/*
	@SpringBootApplication: It is a combination of three annotations @EnableAutoConfiguration, @ComponentScan, and @Configuration.
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2  /* Enabling the Swagger */
@EnableScheduling
@EnableCaching
public class SpringBootApp1Application
{

	public static void main(String[] args)
	{
		log.info("Starting the Spring Boot application 1...");
		SpringApplication.run(SpringBootApp1Application.class, args);
	}

}
